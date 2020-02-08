package ua.training.cruise.repository;

import ua.training.cruise.entity.cruise.Cruise;
import ua.training.cruise.entity.cruise.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findAllByCruise(Cruise cruise);
}
