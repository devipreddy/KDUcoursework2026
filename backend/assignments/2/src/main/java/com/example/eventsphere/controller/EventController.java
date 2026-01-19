package com.example.eventsphere.controller;

import com.example.eventsphere.dto.CreateEventRequest;
import com.example.eventsphere.dto.EventResponse;
import com.example.eventsphere.dto.UpdateEventRequest;
import com.example.eventsphere.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/events")
@SecurityRequirement(name = "Bearer Authentication")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @Operation(summary = "Create a new event (ADMIN only)")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EventResponse> createEvent(
            @RequestBody CreateEventRequest request) {

        EventResponse createdEvent = eventService.createEvent(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdEvent);
    }

    @Operation(summary = "Update event ticket count (ADMIN only)")
    @PutMapping("/{eventId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EventResponse> updateEvent(
            @PathVariable Long eventId,
            @RequestBody UpdateEventRequest request) {

        EventResponse updatedEvent =
                eventService.updateEvent(eventId, request);

        return ResponseEntity.ok(updatedEvent);
    }

    @Operation(summary = "Get all events with pagination (USER)")
    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Page<EventResponse>> getEvents(Pageable pageable) {

        Page<EventResponse> page =
                eventService.getEventsPaginated(pageable);

        return ResponseEntity.ok(page);
    }

    @Operation(summary = "Get event details by ID (USER)")
    @GetMapping("/{eventId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<EventResponse> getEventById(
            @PathVariable Long eventId) {

        EventResponse event =
                eventService.getEventById(eventId);

        return ResponseEntity.ok(event);
    }
}
