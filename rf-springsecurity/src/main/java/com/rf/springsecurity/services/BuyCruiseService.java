package com.rf.springsecurity.services;


import com.rf.springsecurity.domain.cruises.Passenger;
import com.rf.springsecurity.domain.cruises.Ticket;
import com.rf.springsecurity.domain.orders.Order;
import com.rf.springsecurity.domain.ports.Excursion;
import com.rf.springsecurity.dto.OrderDTO;
import com.rf.springsecurity.exceptions.NotEnoughMoney;
import com.rf.springsecurity.exceptions.UnsupportedCruiseName;
import com.rf.springsecurity.exceptions.UnsupportedTicketId;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// TODO clear
@Service
public class BuyCruiseService {
    private final OrderService orderService;
    private final BalanceService balanceService;
    private final PassengerService passengerService;
    private final ExcursionService excursionService;


    @Autowired
    public BuyCruiseService(BalanceService balanceService, PassengerService passengerService,
                            ExcursionService excursionService, OrderService orderService) {
        this.orderService = orderService;
        this.balanceService = balanceService;
        this.passengerService = passengerService;
        this.excursionService = excursionService;
    }


    public void buy(@NonNull OrderDTO orderDTO) throws UnsupportedTicketId, UnsupportedCruiseName, NotEnoughMoney {
        Order order = orderService.castToOrder(orderDTO);
        orderService.addNewOrder(order);
        addPassenger(order);
        balanceService.subBalance(getTotalCruisePrice(order.getTicket(),excursionService.findAllByListOfNames(orderDTO.getExcursionNames())));
    }


    private void addPassenger(@NonNull Order order){
        passengerService.saveNewPassenger(
                Passenger.builder()
                    .firstName(order.getFirstName())
                    .secondName(order.getSecondName())
                    .ticket(order.getTicket())
                    .ship(order.getCruise().getShip())
                    .build());
    }

    private long getTotalCruisePrice(@NonNull Ticket ticket, @NonNull List<Excursion> excursions){
        return ticket.getPrice() + excursionService.getTotalPriceOfExcursions(excursions);
    }
}
