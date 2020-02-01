package com.rf.springsecurity.services;

import com.rf.springsecurity.entity.cruise.Cruise;
import com.rf.springsecurity.entity.cruise.Passenger;
import com.rf.springsecurity.entity.cruise.Ticket;
import com.rf.springsecurity.entity.port.Excursion;
import com.rf.springsecurity.entity.port.Port;
import com.rf.springsecurity.dto.CruiseDescriptionsDTO;
import com.rf.springsecurity.dto.TicketDTO;
import com.rf.springsecurity.exceptions.DataBaseDuplicateConstraint;
import com.rf.springsecurity.exceptions.UnsupportedCruiseName;
import com.rf.springsecurity.repository.CruiseRepository;
import com.rf.springsecurity.repository.ExcursionRepository;
import com.rf.springsecurity.repository.TicketRepository;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CruiseService {

    private static final int ONE_HUNDRED_PERCENT = 100;

    private final CruiseRepository cruiseRepository;
    private final ExcursionRepository excursionRepository;
    private  final TicketRepository ticketRepository;

    @Autowired
    public CruiseService(CruiseRepository cruiseRepository,ExcursionRepository excursionRepository, TicketRepository ticketRepository) {
        this.cruiseRepository = cruiseRepository;
        this.excursionRepository = excursionRepository;
        this.ticketRepository = ticketRepository;
    }

    public List<Cruise> getAllCruises(){
        return cruiseRepository.findAll();
    }

    public List<Excursion> getAllExcursionsByCruiseId(Long id){
        return excursionRepository.findAllByCruiseID(id);
    }

    public Cruise changeCruiseDescription(Cruise cruise, CruiseDescriptionsDTO cruiseDescriptionsDTO){
        cruise.setDescription_ru(cruiseDescriptionsDTO.getDescription_ru());
        cruise.setDescription_eng(cruiseDescriptionsDTO.getDescription_eng());
        return cruiseRepository.save(cruise);
    }
    //todo Ticket
    public Ticket addNewTicketToCruise(TicketDTO ticketDTO ,Cruise cruise) throws DataBaseDuplicateConstraint {
        try{
            return ticketRepository.save(
                    Ticket.builder()
                            .ticketName(ticketDTO.getTicketName())
                            .price(ticketDTO.getPrice())
                            .discount(ticketDTO.getDiscount())
                            .cruise(cruise)
                            .build());
        } catch (DataIntegrityViolationException ex){
            throw new DataBaseDuplicateConstraint(cruise.getCruiseName() + "already has ticket with name: ", ticketDTO.getTicketName());
        }
    }

    public List<Long> listOfTicketPriceWithDiscount(Cruise cruise){
        return cruise.getTickets().stream()
                .map(this::getTicketPriceWithDiscount)
                .collect(Collectors.toList());

    }

    public long getTicketPriceWithDiscount(Ticket ticket){
        return ticket.getPrice() -  Math.round(((double)ticket.getPrice() * ticket.getDiscount()/ONE_HUNDRED_PERCENT));
    }

    //TODo norm Exception
    public Cruise findCruiseById(Long id) throws UnsupportedCruiseName {
        return cruiseRepository.findById(id)
                .orElseThrow(() -> new UnsupportedCruiseName(id.toString()));
    }
}
