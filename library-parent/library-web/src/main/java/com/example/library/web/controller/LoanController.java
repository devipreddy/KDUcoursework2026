// package com.example.library.web.controller;

// import com.example.library.domain.entity.Loan;
// import com.example.library.service.LoanService;
// import org.springframework.http.ResponseEntity;
// import org.springframework.security.access.prepost.PreAuthorize;
// import org.springframework.security.core.Authentication;
// import org.springframework.web.bind.annotation.*;

// import java.util.UUID;

// @RestController
// @RequestMapping("/loans")
// public class LoanController {

//     private final LoanService service;

//     public LoanController(LoanService service) {
//         this.service = service;
//     }

//     // @PostMapping("/{bookId}/borrow")
//     // @PreAuthorize("hasRole('MEMBER')")
//     // public ResponseEntity<Loan> borrow(
//     //         @PathVariable UUID bookId,
//     //         Authentication auth
//     // ) {
//     //     Loan loan = service.borrow(bookId, auth.getName());
//     //     return ResponseEntity.status(201).body(loan);
//     // }

//     @PostMapping("/{bookId}/borrow")
//     @PreAuthorize("hasRole('MEMBER')")
//     public ResponseEntity<LoanResponse> borrow(
//             @PathVariable UUID bookId,
//             Authentication auth) {

//         Loan loan = loanService.borrow(bookId, auth.getName());
//         return ResponseEntity.status(201).body(map(loan));
//     }


//     @PostMapping("/{bookId}/return")
//     @PreAuthorize("hasRole('MEMBER')")
//     public ResponseEntity<Void> returnBook(
//             @PathVariable UUID bookId,
//             Authentication auth
//     ) {
//         service.returnBook(bookId, auth.getName());
//         return ResponseEntity.noContent().build();
//     }
// }

package com.example.library.web.controller;

import com.example.library.api.dto.request.BorrowBookRequest;
import com.example.library.api.dto.request.ReturnBookRequest;
import com.example.library.api.dto.response.LoanResponse;
import com.example.library.domain.entity.Loan;
import com.example.library.service.LoanService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;


import java.util.UUID;

@RestController
@RequestMapping("/loans")
public class LoanController {

    private final LoanService service;

    public LoanController(LoanService service) {
        this.service = service;
    }

    @Operation(
        summary = "Borrow a book",
        description = "Creates a loan for the authenticated member if the book is AVAILABLE."
    )
    @PostMapping("/{bookId}/borrow")
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<LoanResponse> borrow(
            @PathVariable UUID bookId,
            @RequestBody(required = false) BorrowBookRequest ignored,
            Authentication auth
    ) {
        Loan loan = service.borrow(bookId, auth.getName());
        return ResponseEntity.status(201).body(toResponse(loan));
    }

    @PostMapping("/{bookId}/return")
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<Void> returnBook(
            @PathVariable UUID bookId,
            @RequestBody(required = false) ReturnBookRequest ignored,
            Authentication auth
    ) {
        service.returnBook(bookId, auth.getName());
        return ResponseEntity.ok().build();
    }

    /* ---------- MAPPER ---------- */

    private LoanResponse toResponse(Loan loan) {
        LoanResponse r = new LoanResponse();
        r.setId(loan.getId());
        r.setBookId(loan.getBook().getId());
        r.setBorrowerId(loan.getBorrower().getId());
        r.setBorrowedAt(loan.getBorrowedAt());
        r.setReturnedAt(loan.getReturnedAt());
        return r;
    }
}

