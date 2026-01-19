package com.example.eventsphere.repository;

import com.example.eventsphere.entity.Reservation;
import com.example.eventsphere.entity.TicketUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByUser(TicketUser user);
    List<Reservation> findByEvent_Id(Long eventId);
}
