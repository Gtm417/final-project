package ua.training.cruise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.training.cruise.entity.cruise.Cruise;
import ua.training.cruise.entity.cruise.Ticket;

import java.util.List;


public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findAllByCruise(Cruise cruise);
}
