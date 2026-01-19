package com.example.eventsphere.controller;

import com.example.eventsphere.dto.BookingResponse;
import com.example.eventsphere.dto.TransactionResponse;
import com.example.eventsphere.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@SecurityRequirement(name = "Bearer Authentication")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Operation(summary = "Confirm a reservation and create booking (USER)")
    @PostMapping("/confirm/{reservationId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<TransactionResponse> confirmBooking(
            @PathVariable Long reservationId,
            Authentication auth) {

        String username = auth.getName();
        TransactionResponse result =
                bookingService.confirmBooking(reservationId, username);

        URI location = UriComponentsBuilder
                .fromPath("/bookings/{id}")
                .buildAndExpand(result.getBookingId())
                .toUri();

        return ResponseEntity
                .created(location)
                .body(result);
    }

    @Operation(summary = "Cancel a booking (USER)")
    @DeleteMapping("/{bookingId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> cancelBooking(
            @PathVariable Long bookingId,
            Authentication auth) {

        String username = auth.getName();
        bookingService.cancelBooking(bookingId, username);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get booking details (USER)")
    @GetMapping("/{bookingId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BookingResponse> getBooking(
            @PathVariable Long bookingId,
            Authentication auth) {

        String username = auth.getName();
        BookingResponse booking =
                bookingService.getBookingById(bookingId, username);

        return ResponseEntity.ok(booking);
    }

    @Operation(summary = "Get user's bookings (USER)")
    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<BookingResponse>> getUserBookings(
            Authentication auth) {

        String username = auth.getName();
        List<BookingResponse> bookings =
                bookingService.getUserBookings(username);

        return ResponseEntity.ok(bookings);
    }
}
