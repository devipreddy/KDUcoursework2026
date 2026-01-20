package com.railway.booking.api;

import com.railway.booking.api.dto.BookingRequest;
import com.railway.booking.domain.TicketBookedEvent;
import com.railway.booking.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/booking")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<TicketBookedEvent> book(
            @RequestBody BookingRequest request
    ) {
        TicketBookedEvent ticket =
                bookingService.bookTicket(request.getAge());

        return ResponseEntity.accepted().body(ticket);
    }
}
