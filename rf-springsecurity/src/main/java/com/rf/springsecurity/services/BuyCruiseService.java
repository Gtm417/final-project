package com.rf.springsecurity.services;


import com.rf.springsecurity.dto.OrderDTO;
import com.rf.springsecurity.entity.cruise.Cruise;
import com.rf.springsecurity.entity.cruise.Ticket;
import com.rf.springsecurity.entity.order.Order;
import com.rf.springsecurity.entity.port.Excursion;
import com.rf.springsecurity.entity.user.User;
import com.rf.springsecurity.exceptions.NotEnoughMoney;
import com.rf.springsecurity.repository.OrderRepository;
import com.rf.springsecurity.repository.UserRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class BuyCruiseService {
    //TODO find out if ship current capacity allows and add current capacity if allows
    private static final int ONE_HUNDRED_PERCENT = 100;

    private  OrderRepository orderRepository;
    private UserRepository userRepository;


    @Autowired
    public BuyCruiseService(OrderRepository orderRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public boolean buyCruise(@NonNull OrderDTO orderDTO, @NonNull Cruise cruise, @NonNull User user) throws NotEnoughMoney {
        userRepository.save(
                subBalance(
                        user,
                        getTotalCruisePrice(
                                orderDTO.getTicket(),
                                orderDTO.getExcursions()
                        ))
        );
        addNewOrder(orderDTO, cruise, user);
        return true;
    }

    private User subBalance( User user, long orderSum) throws NotEnoughMoney {
        long totalBalance = user.getBalance() - orderSum;
        if (totalBalance < 0) {
            throw new NotEnoughMoney("Not Enough Money " + user.getBalance());
        }
        user.setBalance(user.getBalance() - orderSum);
        return user;
    }


    private Order addNewOrder(OrderDTO orderDTO, Cruise cruise, User user){
       return orderRepository.save(Order.builder()
               .cruise(cruise)
               .user(user)
               .firstName(orderDTO.getFirstName())
               .secondName(orderDTO.getSecondName())
               .ticket(orderDTO.getTicket())
               .build());
    }


    private long getTicketPriceWithDiscount(Ticket ticket){
        return ticket.getPrice() -  Math.round(((double)ticket.getPrice() * ticket.getDiscount()/ONE_HUNDRED_PERCENT));
    }

    private long getTotalPriceOfExcursions(Set<Excursion> excursions){
        return excursions.stream().mapToLong(Excursion::getPrice).sum();
    }

    private long getTotalCruisePrice(@NonNull Ticket ticket, @NonNull Set<Excursion> excursions){
        return  getTicketPriceWithDiscount(ticket) + getTotalPriceOfExcursions(excursions);
    }
}
