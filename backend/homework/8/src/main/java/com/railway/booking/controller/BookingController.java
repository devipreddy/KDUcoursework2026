package com.railway.booking.api;

import com.railway.booking.api.dto.BookingRequest;
import com.railway.booking.domain.TicketBookedEvent;
import com.railway.booking.service.BookingService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/booking")
public class BookingController {

    private static final Logger log =
            LoggerFactory.getLogger(BookingController.class);

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<TicketBookedEvent> book(
            @Valid @RequestBody BookingRequest request
    ) {
        log.info("Received booking request");

        TicketBookedEvent ticket =
                bookingService.bookTicket(request.getAge());

        return ResponseEntity.accepted().body(ticket);
    }
}
