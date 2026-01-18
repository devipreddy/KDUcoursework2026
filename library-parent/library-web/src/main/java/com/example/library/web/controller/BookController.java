// package com.example.library.web.controller;

// import com.example.library.domain.entity.Book;
// import com.example.library.service.BookService;
// import org.springframework.http.ResponseEntity;
// import org.springframework.security.access.prepost.PreAuthorize;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;
// import java.util.UUID;

// @RestController
// @RequestMapping("/books")
// public class BookController {

//     private final BookService service;

//     public BookController(BookService service) {
//         this.service = service;
//     }

//     // @PostMapping
//     // @PreAuthorize("hasRole('LIBRARIAN')")
//     // public ResponseEntity<Book> create(@RequestParam String title) {
//     //     Book created = service.createBook(title);
//     //     return ResponseEntity.status(201).body(created);
//     // }

//     @PostMapping
//     @PreAuthorize("hasRole('LIBRARIAN')")
//     public ResponseEntity<BookResponse> create(
//             @Valid @RequestBody CreateBookRequest req) {

//         Book book = bookService.createBook(req.getTitle());
//         return ResponseEntity.status(201).body(map(book));
//     }


//     // @PatchMapping("/{id}/catalog")
//     // @PreAuthorize("hasRole('LIBRARIAN')")
//     // public ResponseEntity<Book> catalog(@PathVariable UUID id) {
//     //     return ResponseEntity.ok(service.catalog(id));
//     // }

//     @PatchMapping("/{id}/catalog")
//     @PreAuthorize("hasRole('LIBRARIAN')")
//     public ResponseEntity<BookResponse> catalog(@PathVariable UUID id) {
//         return ResponseEntity.ok(map(bookService.catalog(id)));
//     }


//     @GetMapping
//     @PreAuthorize("hasAnyRole('LIBRARIAN','MEMBER')")
//     public ResponseEntity<List<Book>> list() {
//         return ResponseEntity.ok(service.findAll());
//     }
// }
package com.example.library.web.controller;

import com.example.library.api.dto.request.CreateBookRequest;
import com.example.library.api.dto.response.BookResponse;
import com.example.library.domain.entity.Book;
import com.example.library.service.BookService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import com.example.library.domain.enums.BookStatus;
import java.util.List;
import java.util.UUID;
import io.swagger.v3.oas.annotations.Operation;


@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    @Operation(
        summary = "Create a new book",
        description = "Creates a book in PROCESSING state. Librarian only."
    )
    @PostMapping
    @PreAuthorize("hasRole('LIBRARIAN')")
    public ResponseEntity<BookResponse> create(
            @Valid @RequestBody CreateBookRequest request
    ) {
        Book book = service.createBook(request.getTitle());
        return ResponseEntity.status(201).body(toResponse(book));
    }

    @Operation(
        summary = "Catalog a book",
        description = "Moves a book from PROCESSING to AVAILABLE. Librarian only."
    )
    @PatchMapping("/{id}/catalog")
    @PreAuthorize("hasRole('LIBRARIAN')")
    public ResponseEntity<BookResponse> catalog(@PathVariable UUID id) {
        Book book = service.catalog(id);
        return ResponseEntity.ok(toResponse(book));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('LIBRARIAN','MEMBER')")
    public ResponseEntity<List<BookResponse>> list() {
        return ResponseEntity.ok(
                service.findAll().stream().map(this::toResponse).toList()
        );
    }

    @GetMapping("/page")
    public ResponseEntity<Page<Book>> getBooks(
            @RequestParam(required = false) BookStatus status,
            @RequestParam(required = false) String titleContains,
            @PageableDefault(size = 20) Pageable pageable
    ) {

        Page<Book> result = service.getBooks(
                status,
                titleContains,
                pageable
        );

        return ResponseEntity.ok(result);
    }

    /* ---------- MAPPER ---------- */

    private BookResponse toResponse(Book book) {
        BookResponse r = new BookResponse();
        r.setId(book.getId());
        r.setTitle(book.getTitle());
        r.setStatus(
            com.example.library.api.enums.BookStatus.valueOf(
                book.getStatus().name()
            )
        );
        r.setCreatedAt(book.getCreatedAt());
        r.setUpdatedAt(book.getUpdatedAt());
        return r;
    }



}
