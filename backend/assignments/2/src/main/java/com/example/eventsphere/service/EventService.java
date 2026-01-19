package com.example.eventsphere.service;

import com.example.eventsphere.dto.CreateEventRequest;
import com.example.eventsphere.dto.EventResponse;
import com.example.eventsphere.dto.UpdateEventRequest;
import com.example.eventsphere.entity.Event;
import com.example.eventsphere.repository.EventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EventService {

    private static final Logger log =
            LoggerFactory.getLogger(EventService.class);

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Transactional
    public EventResponse createEvent(CreateEventRequest request) {

        validateCreateRequest(request);

        Event event = new Event(
                request.getName(),
                request.getDescription(),
                request.getLocation(),
                request.getDate(),
                request.getTicketCount()
        );

        Event saved = eventRepository.save(event);

        log.info("Event created: id={}, name={}", saved.getId(), saved.getName());

        return toResponse(saved);
    }

    @Transactional
    public EventResponse updateEvent(Long eventId, UpdateEventRequest request) {

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() ->
                        new RuntimeException("Event not found: " + eventId));

        if (request.getTicketCount() != null) {
            validateTicketCount(request.getTicketCount());
            event.setAvailableTickets(request.getTicketCount());
        }

        Event updated = eventRepository.save(event);

        log.info("Event updated: id={}", updated.getId());

        return toResponse(updated);
    }

    @Transactional(readOnly = true)
    public Page<EventResponse> getEventsPaginated(Pageable pageable) {
        return eventRepository.findAll(pageable)
                .map(this::toResponse);
    }

    @Transactional(readOnly = true)
    public EventResponse getEventById(Long eventId) {

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() ->
                        new RuntimeException("Event not found: " + eventId));

        return toResponse(event);
    }

    @Transactional
    public void reserveTickets(Long eventId, Long ticketCount) {

        Event event = eventRepository.findByIdForUpdate(eventId)
                .orElseThrow(() ->
                        new RuntimeException("Event not found: " + eventId));

        if (event.getAvailableTickets() < ticketCount) {
            throw new IllegalArgumentException(
                    "Not enough tickets available for event " + eventId);
        }

        event.setAvailableTickets(
                event.getAvailableTickets() - ticketCount);

        eventRepository.save(event);
    }

    @Transactional
    public void restoreTickets(Long eventId, Long ticketCount) {

        Event event = eventRepository.findByIdForUpdate(eventId)
                .orElseThrow(() ->
                        new RuntimeException("Event not found: " + eventId));

        event.setAvailableTickets(
                event.getAvailableTickets() + ticketCount);

        eventRepository.save(event);
    }


    private void validateCreateRequest(CreateEventRequest request) {
        if (request.getName() == null || request.getName().isBlank()) {
            throw new IllegalArgumentException("Event name is required");
        }
        validateTicketCount(request.getTicketCount());
    }

    private void validateTicketCount(Long count) {
        if (count == null || count < 0) {
            throw new IllegalArgumentException(
                    "Ticket count must be zero or greater");
        }
    }

    private EventResponse toResponse(Event event) {
        return new EventResponse(
                event.getId(),
                event.getName(),
                event.getDescription(),
                event.getLocation(),
                event.getDate(),
                event.getAvailableTickets()
        );
    }
}
