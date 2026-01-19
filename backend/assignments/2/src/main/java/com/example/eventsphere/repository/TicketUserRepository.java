package com.example.eventsphere.repository;

import com.example.eventsphere.entity.TicketUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TicketUserRepository extends JpaRepository<TicketUser, Long> {
    Optional<TicketUser> findByUsername(String username);
}
