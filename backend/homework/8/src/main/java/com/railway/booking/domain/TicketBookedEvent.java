package com.railway.booking.domain;

import java.time.Instant;
import java.util.UUID;

public record TicketBookedEvent(
        UUID eventId,
        String bookingId,
        String seatNumber,
        int age,
        Instant createdAt
) {}
