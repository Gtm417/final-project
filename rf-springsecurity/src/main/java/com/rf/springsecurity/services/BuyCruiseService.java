package com.rf.springsecurity.services;


import com.rf.springsecurity.domain.cruises.Passenger;
import com.rf.springsecurity.domain.orders.Order;
import com.rf.springsecurity.dto.OrderDTO;
import com.rf.springsecurity.exceptions.UnsupportedCruiseName;
import com.rf.springsecurity.repository.CruiseRepository;
import com.rf.springsecurity.repository.OrderRepository;
import com.rf.springsecurity.repository.PassengerRepository;
import com.rf.springsecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// TODO clear
@Service
public class BuyCruiseService {

    private OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CruiseRepository cruiseRepository;
    private PassengerRepository passengerRepository;

    @Autowired
    public BuyCruiseService(OrderRepository orderRepository, CruiseRepository cruiseRepository,
                            UserRepository userRepository, PassengerRepository passengerRepository) {
        this.orderRepository = orderRepository;
        this.cruiseRepository = cruiseRepository;
        this.userRepository = userRepository;
        this.passengerRepository = passengerRepository;
    }


    public void buy(OrderDTO orderDTO) throws UnsupportedCruiseName {
        addNewOrder(orderDTO);
        addPassenger(orderDTO);
    }

    private void addNewOrder(OrderDTO orderDTO) throws UnsupportedCruiseName {
        //TODO Переедалть орЕлс
        orderRepository.save(Order.builder()
                .cruise(orderDTO.getCruise())
                .user(orderDTO.getUser())
                .ticket(orderDTO.getTicket())
                .build());
    }
    private void addPassenger(OrderDTO orderDTO) {
        passengerRepository.save(Passenger.builder()
                .firstName(orderDTO.getFirstName())
                .secondName(orderDTO.getSecondName())
                .ticket(orderDTO.getTicket())
                .ship(orderDTO.getCruise().getShip())
                .build());
    }



}
