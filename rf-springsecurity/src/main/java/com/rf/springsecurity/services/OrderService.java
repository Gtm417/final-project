package com.rf.springsecurity.services;

import com.rf.springsecurity.entity.cruise.Cruise;
import com.rf.springsecurity.entity.order.Order;
import com.rf.springsecurity.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

}
