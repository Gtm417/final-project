package com.rf.springsecurity.services;

import com.rf.springsecurity.entity.cruises.Ticket;
import com.rf.springsecurity.exceptions.UnsupportedTicketId;
import com.rf.springsecurity.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketService {
    private static final int ONE_HUNDRED_PERCENT = 100;

    private final TicketRepository ticketRepository;

    @Autowired
    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }


    public Ticket getTicketById(long id) throws UnsupportedTicketId {
        return ticketRepository.findById(id).orElseThrow(() -> new UnsupportedTicketId("There is no ticket with such id: " + id));
    }

    public long getTicketPriceWithDiscount(Ticket ticket){
        return ticket.getPrice() -  Math.round(((double)ticket.getPrice() * ticket.getDiscount()/ONE_HUNDRED_PERCENT));
    }
    public void addNewTicket(Ticket ticket){
        ticketRepository.save(ticket);
    }
}
