package com.rf.springsecurity.services;


import com.rf.springsecurity.domain.cruises.Cruise;
import com.rf.springsecurity.domain.cruises.Passenger;
import com.rf.springsecurity.domain.cruises.Ship;
import com.rf.springsecurity.domain.orders.Order;
import com.rf.springsecurity.domain.orders.OrderStatus;
import com.rf.springsecurity.dto.OrderDTO;
import com.rf.springsecurity.exceptions.UnhandledCruiseName;
import com.rf.springsecurity.repository.CruiseRepository;
import com.rf.springsecurity.repository.OrderRepository;
import com.rf.springsecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BuyCruiseService {

    private OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CruiseRepository cruiseRepository;

    @Autowired
    public BuyCruiseService(OrderRepository orderRepository, CruiseRepository cruiseRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.cruiseRepository = cruiseRepository;
        this.userRepository = userRepository;
    }


    public void buy(OrderDTO orderDTO) throws UnhandledCruiseName {
        addNewOrder(orderDTO);
        addPassenger(orderDTO);
    }

    private void addNewOrder(OrderDTO orderDTO) throws UnhandledCruiseName{
        //TODO Переедалть орЕлс
        orderRepository.save(Order.builder()
                .cruise(cruiseRepository.findByCruiseName(orderDTO.getCruiseName())
                        .orElseThrow(() -> new UnhandledCruiseName(orderDTO.getCruiseName())))
                .user(userRepository.findByLogin(orderDTO.getUserLogin())
                        .orElse(null))
                .ticket(orderDTO.getTicket()).build());
                //.status(OrderStatus.AWAITING).build());
    }
    private void addPassenger(OrderDTO orderDTO) throws UnhandledCruiseName {
        Cruise cruise = cruiseRepository.findByCruiseName(orderDTO.getCruiseName())
                .orElseThrow(() -> new UnhandledCruiseName(orderDTO.getCruiseName()));
        List<Passenger> passengers =  cruise.getShip().getListOfPassenger();
        passengers.add(Passenger.builder()
                .firstName(orderDTO.getFirstName())
                .secondName(orderDTO.getSecondName())
                .ticket(orderDTO.getTicket())
                .build());
        Ship ship = cruise.getShip();
        ship.setListOfPassenger(passengers);
        cruise.setShip(ship);
        //cruise.getShip().setListOfPassenger(passengers);
        cruiseRepository.save(cruise);
    }



}
