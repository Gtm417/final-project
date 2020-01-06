package com.rf.springsecurity.repository;

import com.rf.springsecurity.domain.cruises.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
//    Optional<Ticket> findById(Long id);
}
