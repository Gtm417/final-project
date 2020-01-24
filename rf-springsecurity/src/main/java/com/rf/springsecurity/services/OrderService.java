package com.rf.springsecurity.services;

import com.rf.springsecurity.dto.OrdersDTO;
import com.rf.springsecurity.entity.cruise.Cruise;
import com.rf.springsecurity.entity.order.Order;
import com.rf.springsecurity.entity.user.User;
import com.rf.springsecurity.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> findAllOrdersByCruise(Cruise cruise){
        return orderRepository.findAllByCruise(cruise);
    }

    public List<OrdersDTO> findAllOrdersByUser(User user){
        return orderRepository.findAllByUser(user);
    }

}
