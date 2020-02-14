package ua.training.cruise.service.mapper;

import org.springframework.stereotype.Component;
import ua.training.cruise.dto.TicketDTO;
import ua.training.cruise.entity.cruise.Ticket;

@Component
public class TicketMapper {
    public Ticket mapToEntity(TicketDTO ticketDTO) {
        return Ticket.builder()
                .ticketName(ticketDTO.getTicketName())
                .price(ticketDTO.getPrice())
                .discount(ticketDTO.getDiscount())
                .build();
    }
}
