package com.rf.springsecurity.services;

import com.rf.springsecurity.domain.cruises.Cruise;
import com.rf.springsecurity.domain.cruises.Passenger;
import com.rf.springsecurity.domain.cruises.Ticket;
import com.rf.springsecurity.domain.ports.Excursion;
import com.rf.springsecurity.domain.ports.Port;
import com.rf.springsecurity.dto.CruiseDescriptionsDTO;
import com.rf.springsecurity.dto.TicketDTO;
import com.rf.springsecurity.exceptions.UnsupportedCruiseName;
import com.rf.springsecurity.repository.CruiseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CruiseService {

    private CruiseRepository cruiseRepository;
    private final TicketService ticketService;

    @Autowired
    public CruiseService(CruiseRepository cruiseRepository, TicketService ticketService) {
        this.cruiseRepository =cruiseRepository;
        this.ticketService = ticketService;
    }

    public Cruise getCruiseDataByName(String name) throws UnsupportedCruiseName {
        return cruiseRepository.findByCruiseName(name)
                .orElseThrow(() -> new UnsupportedCruiseName(name));
    }

    public List<Cruise> getAllCruises(){
        return cruiseRepository.findAll();
    }

    public List<Excursion> getAllExcursionsInCruise(Cruise cruise){
        return cruise.getPorts().stream()
                .map((Port::getExcursions))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    public void changeCruiseDescription(Cruise cruise, CruiseDescriptionsDTO cruiseDescriptionsDTO){
        cruise.setDescription_ru(cruiseDescriptionsDTO.getDescription_ru());
        cruise.setDescription_eng(cruiseDescriptionsDTO.getDescription_eng());
        cruiseRepository.save(cruise);
    }

    public void addNewTicketToCruise(Cruise cruise, TicketDTO ticketDTO){
        ticketService.addNewTicket(Ticket.builder()
                .ticketName(ticketDTO.getTicketName())
                .price(ticketDTO.getPrice())
                .discount(ticketDTO.getDiscount())
                .cruise(cruise)
                .build());
    }

    public List<Passenger> getCurrentListOfPassengersAtCruise(String name) throws UnsupportedCruiseName {
       return getCruiseDataByName(name).getShip().getListOfPassenger();
    }
}
