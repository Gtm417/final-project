package com.rf.springsecurity.services;

import com.rf.springsecurity.entity.cruise.Cruise;
import com.rf.springsecurity.entity.cruise.Passenger;
import com.rf.springsecurity.entity.cruise.Ticket;
import com.rf.springsecurity.entity.port.Excursion;
import com.rf.springsecurity.entity.port.Port;
import com.rf.springsecurity.dto.CruiseDescriptionsDTO;
import com.rf.springsecurity.dto.TicketDTO;
import com.rf.springsecurity.exceptions.UnsupportedCruiseName;
import com.rf.springsecurity.repository.CruiseRepository;
import com.rf.springsecurity.repository.ExcursionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CruiseService {

    private static final int ONE_HUNDRED_PERCENT = 100;
    //TODO repos
    private CruiseRepository cruiseRepository;
    private final ExcursionRepository excursionRepository;
    private final TicketService ticketService;

    @Autowired
    public CruiseService(CruiseRepository cruiseRepository, TicketService ticketService, ExcursionRepository excursionRepository) {
        this.cruiseRepository =cruiseRepository;
        this.ticketService = ticketService;
        this.excursionRepository = excursionRepository;
    }

    public Cruise getCruiseDataByName(String name) throws UnsupportedCruiseName {
        return cruiseRepository.findByCruiseName(name)
                .orElseThrow(() -> new UnsupportedCruiseName(name));
    }

    public List<Cruise> getAllCruises(){
        return cruiseRepository.findAll();
    }

    public List<Excursion> getAllExcursionsByCruiseId(Long id){
        return excursionRepository.findAllByCruiseID(id);
    }

    public void changeCruiseDescription(Cruise cruise, CruiseDescriptionsDTO cruiseDescriptionsDTO){
        cruise.setDescription_ru(cruiseDescriptionsDTO.getDescription_ru());
        cruise.setDescription_eng(cruiseDescriptionsDTO.getDescription_eng());
        cruiseRepository.save(cruise);
    }

    public void addNewTicketToCruise(Cruise cruise, TicketDTO ticketDTO){
        ticketService.addNewTicket(
                Ticket.builder()
                .ticketName(ticketDTO.getTicketName())
                .price(ticketDTO.getPrice())
                .discount(ticketDTO.getDiscount())
                .cruise(cruise)
                .build());
    }

    //TODO 1 request
    public List<Passenger> getCurrentListOfPassengersAtCruise(String name) throws UnsupportedCruiseName {
       return getCruiseDataByName(name).getShip().getListOfPassenger();
    }

    public List<Long> listOfTicketPriceWithDiscount(Cruise cruise){
        return cruise.getTickets().stream()
                .map(this::getTicketPriceWithDiscount).collect(Collectors.toList());

    }

    private long getTicketPriceWithDiscount(Ticket ticket){
        return ticket.getPrice() -  Math.round(((double)ticket.getPrice() * ticket.getDiscount()/ONE_HUNDRED_PERCENT));
    }

    //TODo norm Exception
    public Cruise findCruiseById(Long id) throws UnsupportedCruiseName {
        return cruiseRepository.findById(id)
                .orElseThrow(() -> new UnsupportedCruiseName(id.toString()));
    }
}
