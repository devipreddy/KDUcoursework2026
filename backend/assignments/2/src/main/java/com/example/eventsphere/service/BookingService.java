package com.example.eventsphere.service;

import com.example.eventsphere.dto.BookingResponse;
import com.example.eventsphere.dto.TransactionResponse;
import com.example.eventsphere.entity.Booking;
import com.example.eventsphere.entity.Reservation;
import com.example.eventsphere.entity.TicketUser;
import com.example.eventsphere.entity.Transaction;
import com.example.eventsphere.repository.BookingRepository;
import com.example.eventsphere.repository.ReservationRepository;
import com.example.eventsphere.repository.TicketUserRepository;
import com.example.eventsphere.repository.TransactionRepository;
import com.example.eventsphere.util.RetryableOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookingService {

    private static final Logger log =
            LoggerFactory.getLogger(BookingService.class);

    private static final int MAX_RETRIES = 3;
    private static final long INITIAL_DELAY_MS = 50;

    private final BookingRepository bookingRepository;
    private final ReservationRepository reservationRepository;
    private final TicketUserRepository userRepository;
    private final TransactionRepository transactionRepository;
    private final EventService eventService;

    public BookingService(
            BookingRepository bookingRepository,
            ReservationRepository reservationRepository,
            TicketUserRepository userRepository,
            TransactionRepository transactionRepository,
            EventService eventService) {

        this.bookingRepository = bookingRepository;
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
        this.eventService = eventService;
    }

    @Transactional
    public TransactionResponse confirmBooking(Long reservationId, String username) {

        return RetryableOperation.executeWithRetry(() -> {

            Reservation reservation = reservationRepository.findById(reservationId)
                    .orElseThrow(() ->
                            new RuntimeException("Reservation not found: " + reservationId));

            if (!reservation.getUser().getUsername().equals(username)) {
                throw new RuntimeException("You can only confirm your own reservations");
            }

            if (reservation.getStatus() == Reservation.ReservationStatus.CONFIRMED) {
                throw new RuntimeException("Reservation already confirmed");
            }

            Booking booking = new Booking(
                    reservation.getEvent(),
                    reservation.getUser(),
                    reservation.getTicketCount()
            );

            Booking savedBooking = bookingRepository.save(booking);

            reservation.setStatus(Reservation.ReservationStatus.CONFIRMED);
            reservationRepository.save(reservation);

            double amount = reservation.getTicketCount() * 100.0;
            Transaction transaction =
                    new Transaction(savedBooking, reservation.getUser(), amount);

            Transaction savedTransaction =
                    transactionRepository.save(transaction);

            log.info(
                    "Booking confirmed: bookingId={}, user={}",
                    savedBooking.getId(), username
            );

            return new TransactionResponse(
                    savedTransaction.getTransactionId(),
                    savedBooking.getId(),
                    savedTransaction.getAmount(),
                    savedTransaction.getStatus().name(),
                    savedTransaction.getTransactionDate()
            );

        }, MAX_RETRIES, INITIAL_DELAY_MS);
    }

    @Transactional
    public void cancelBooking(Long bookingId, String username) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() ->
                        new RuntimeException("Booking not found: " + bookingId));

        if (!booking.getTicketUser().getUsername().equals(username)) {
            throw new RuntimeException("You can only cancel your own bookings");
        }

        if (booking.getStatus() == Booking.BookingStatus.CANCELLED) {
            throw new RuntimeException("Booking already cancelled");
        }

        // restore tickets back to the event
        eventService.restoreTickets(
                booking.getEvent().getId(),
                Long.valueOf(booking.getNumberOfTickets())
        );

        booking.setStatus(Booking.BookingStatus.CANCELLED);
        bookingRepository.save(booking);

        cancelSuccessfulTransaction(bookingId);
    }

    @Transactional(readOnly = true)
    public BookingResponse getBookingById(Long bookingId, String username) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() ->
                        new RuntimeException("Booking not found: " + bookingId));

        if (!booking.getTicketUser().getUsername().equals(username)) {
            throw new RuntimeException("You can only view your own bookings");
        }

        return toResponse(booking);
    }

    @Transactional(readOnly = true)
    public List<BookingResponse> getUserBookings(String username) {

        TicketUser user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException("User not found: " + username));

        return bookingRepository.findByTicketUser(user)
                .stream()
                .map(this::toResponse)
                .toList();
    }


    private void cancelSuccessfulTransaction(Long bookingId) {

        List<Transaction> transactions = transactionRepository.findAll();

        for (Transaction tx : transactions) {
            if (tx.getBooking().getId().equals(bookingId)
                    && tx.getStatus() == Transaction.TransactionStatus.SUCCESS) {

                tx.setStatus(Transaction.TransactionStatus.CANCELLED);
                transactionRepository.save(tx);
                break;
            }
        }
    }

    private BookingResponse toResponse(Booking booking) {
        return new BookingResponse(
                booking.getId(),
                booking.getEvent().getId(),
                booking.getEvent().getName(),
                booking.getNumberOfTickets(),
                booking.getStatus().name()
        );
    }
}
