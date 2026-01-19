package com.example.eventsphere.service;

import com.example.eventsphere.dto.CreateReservationRequest;
import com.example.eventsphere.dto.ReservationResponse;
import com.example.eventsphere.entity.Event;
import com.example.eventsphere.entity.Reservation;
import com.example.eventsphere.entity.TicketUser;
import com.example.eventsphere.repository.EventRepository;
import com.example.eventsphere.repository.ReservationRepository;
import com.example.eventsphere.repository.TicketUserRepository;
import com.example.eventsphere.util.RetryableOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReservationService {

    private static final Logger log =
            LoggerFactory.getLogger(ReservationService.class);

    private static final int MAX_RETRIES = 3;
    private static final long INITIAL_DELAY_MS = 50;

    private final ReservationRepository reservationRepository;
    private final EventRepository eventRepository;
    private final TicketUserRepository userRepository;
    private final EventService eventService;

    public ReservationService(
            ReservationRepository reservationRepository,
            EventRepository eventRepository,
            TicketUserRepository userRepository,
            EventService eventService) {

        this.reservationRepository = reservationRepository;
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.eventService = eventService;
    }

    @Transactional
    public ReservationResponse createReservation(
            CreateReservationRequest request,
            String username) {

        validateTicketCount(Long.valueOf(request.getTicketCount()));

        Event event = eventRepository.findById(request.getEventId())
                .orElseThrow(() ->
                        new RuntimeException("Event not found: " + request.getEventId()));

        TicketUser user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException("User not found: " + username));

        return RetryableOperation.executeWithRetry(() -> {

            log.debug(
                    "Creating reservation for user={}, event={}, tickets={}",
                    username, event.getId(), request.getTicketCount()
            );

            eventService.reserveTickets(Long.valueOf(event.getId()), Long.valueOf(request.getTicketCount()));

            Reservation reservation =
                    new Reservation(event, user, request.getTicketCount());

            Reservation saved =
                    reservationRepository.save(reservation);

            log.info(
                    "Reservation {} created for user {}",
                    saved.getId(), username
            );

            return toResponse(saved);

        }, MAX_RETRIES, INITIAL_DELAY_MS);
    }

    @Transactional
    public ReservationResponse updateReservation(
            Long reservationId,
            Long newTicketCount,
            String username) {

        validateTicketCount(newTicketCount);

        Reservation reservation =
                getReservationOwnedByUser(reservationId, username);

        if (reservation.getStatus() == Reservation.ReservationStatus.CONFIRMED) {
            throw new RuntimeException("Confirmed reservations cannot be modified");
        }

        if (reservation.getStatus() == Reservation.ReservationStatus.CANCELLED) {
            throw new RuntimeException("Cancelled reservations cannot be modified");
        }

        long diff = newTicketCount - reservation.getTicketCount();

        if (diff > 0) {
            eventService.reserveTickets(
                    reservation.getEvent().getId(), diff);
        } else if (diff < 0) {
            eventService.restoreTickets(
                    reservation.getEvent().getId(), Math.abs(diff));
        }

        reservation.setTicketCount(newTicketCount.intValue());
        Reservation updated =
                reservationRepository.save(reservation);

        return toResponse(updated);
    }

    @Transactional
    public void deleteReservation(Long reservationId, String username) {

        Reservation reservation =
                getReservationOwnedByUser(reservationId, username);

        if (reservation.getStatus() == Reservation.ReservationStatus.CONFIRMED) {
            throw new RuntimeException(
                    "Confirmed reservations must be cancelled via booking");
        }

        if (reservation.getStatus() == Reservation.ReservationStatus.PENDING) {
            eventService.restoreTickets(
                    reservation.getEvent().getId(),
                    Long.valueOf(reservation.getTicketCount()));
        }

        reservationRepository.delete(reservation);
    }

    @Transactional(readOnly = true)
    public ReservationResponse getReservationById(
            Long reservationId,
            String username) {

        Reservation reservation =
                getReservationOwnedByUser(reservationId, username);

        return toResponse(reservation);
    }

    @Transactional(readOnly = true)
    public List<ReservationResponse> getUserReservations(String username) {

        TicketUser user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException("User not found: " + username));

        return reservationRepository.findByUser(user)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private Reservation getReservationOwnedByUser(
            Long reservationId,
            String username) {

        Reservation reservation =
                reservationRepository.findById(reservationId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Reservation not found: " + reservationId));

        if (!reservation.getUser().getUsername().equals(username)) {
            throw new RuntimeException(
                    "You are not allowed to access this reservation");
        }

        return reservation;
    }

    private void validateTicketCount(Long count) {
        if (count == null || count <= 0) {
            throw new IllegalArgumentException(
                    "Ticket count must be greater than zero");
        }
    }

    private ReservationResponse toResponse(Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(),
                reservation.getEvent().getId(),
                reservation.getEvent().getName(),
                reservation.getTicketCount(),
                reservation.getStatus().name()
        );
    }
}
