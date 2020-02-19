package ua.training.cruise.service;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ua.training.cruise.dto.TicketDTO;
import ua.training.cruise.entity.cruise.Cruise;
import ua.training.cruise.entity.cruise.Ticket;
import ua.training.cruise.exception.DataBaseDuplicateConstraint;
import ua.training.cruise.repository.TicketRepository;
import ua.training.cruise.service.mapper.TicketMapper;

import java.util.List;

@Log4j
@Service
public class TicketService {
    private static final int ONE_HUNDRED_PERCENT = 100;

    private final TicketRepository ticketRepository;
    private final TicketMapper mapper;

    @Autowired
    public TicketService(TicketRepository ticketRepository, TicketMapper mapper) {
        this.ticketRepository = ticketRepository;
        this.mapper = mapper;
    }


    public Ticket addNewTicketToCruise(TicketDTO ticketDTO, Cruise cruise) throws DataBaseDuplicateConstraint {
        Ticket ticket = mapper.mapToEntity(ticketDTO);
        ticket.setPriceWithDiscount(calcTicketPriceWithDiscount(ticketDTO));
        ticket.setCruise(cruise);
        try {
            return ticketRepository.save(ticket);
        } catch (DataIntegrityViolationException ex) {
            throw new DataBaseDuplicateConstraint(cruise.getCruiseName() + "already has ticket with name: " + ticketDTO.getTicketName());
        }
    }

    private long calcTicketPriceWithDiscount(TicketDTO ticket) {
        return ticket.getPrice() - Math.round(((double) ticket.getPrice() * ticket.getDiscount() / ONE_HUNDRED_PERCENT));
    }

    public List<Ticket> showAllTicketsForCruise(Cruise cruise) {
        return ticketRepository.findAllByCruise(cruise);

    }
}
