package com.rf.springsecurity.services;


import com.rf.springsecurity.domain.orders.Order;
import com.rf.springsecurity.dto.OrderDTO;
import com.rf.springsecurity.exceptions.UnsupportedCruiseName;
import com.rf.springsecurity.exceptions.UnsupportedTicketId;
import com.rf.springsecurity.repository.OrderRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;
    private final CruiseService cruiseService;
    private final TicketService ticketService;


    public OrderService(OrderRepository orderRepository, UserService userService, CruiseService cruiseService, TicketService ticketService) {
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.cruiseService = cruiseService;
        this.ticketService = ticketService;
    }

    public void addNewOrder(Order order){
        orderRepository.save(order);
    }

    public Order castToOrder(@NonNull OrderDTO orderDTO) throws UnsupportedCruiseName, UnsupportedTicketId {
        return Order.builder()
                .firstName(orderDTO.getFirstName())
                .secondName(orderDTO.getSecondName())
                .cruise(cruiseService.getCruiseDataByName(orderDTO.getCruiseName()))
                .user(userService.getAuthenticatedUser())
                .ticket(ticketService.getTicketById(orderDTO.getTicket_id()))
                .build();
    }
}
