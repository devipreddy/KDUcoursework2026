package com.example.eventsphere.repository;

import com.example.eventsphere.entity.Booking;
import com.example.eventsphere.entity.TicketUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByTicketUser(TicketUser user);
    List<Booking> findByEvent_Id(Long eventId);
}
