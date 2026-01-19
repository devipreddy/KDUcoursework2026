package com.example.eventsphere.controller;

import com.example.eventsphere.dto.CreateReservationRequest;
import com.example.eventsphere.dto.ReservationResponse;
import com.example.eventsphere.service.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
@SecurityRequirement(name = "Bearer Authentication")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @Operation(summary = "Create a ticket reservation (USER)")
    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ReservationResponse> createReservation(
            @RequestBody CreateReservationRequest request,
            Authentication auth) {

        // authenticated username comes from JWT / security context
        String username = auth.getName();

        ReservationResponse created =
                reservationService.createReservation(request, username);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(created);
    }

    @Operation(summary = "Update a reservation (USER)")
    @PutMapping("/{reservationId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ReservationResponse> updateReservation(
            @PathVariable Long reservationId,
            @RequestParam Integer ticketCount,
            Authentication auth) {

        String username = auth.getName();

        ReservationResponse updated =
                reservationService.updateReservation(reservationId, Long.valueOf(ticketCount), username);

        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Delete a reservation (USER)")
    @DeleteMapping("/{reservationId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> deleteReservation(
            @PathVariable Long reservationId,
            Authentication auth) {

        String username = auth.getName();
        reservationService.deleteReservation(reservationId, username);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get reservation details (USER)")
    @GetMapping("/{reservationId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ReservationResponse> getReservation(
            @PathVariable Long reservationId,
            Authentication auth) {

        String username = auth.getName();
        ReservationResponse reservation =
                reservationService.getReservationById(reservationId, username);

        return ResponseEntity.ok(reservation);
    }

    @Operation(summary = "Get user's reservations (USER)")
    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<ReservationResponse>> getUserReservations(
            Authentication auth) {

        String username = auth.getName();
        List<ReservationResponse> reservations =
                reservationService.getUserReservations(username);

        return ResponseEntity.ok(reservations);
    }
}
