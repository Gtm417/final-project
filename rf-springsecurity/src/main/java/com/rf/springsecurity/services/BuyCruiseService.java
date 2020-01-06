package com.rf.springsecurity.services;


import com.rf.springsecurity.domain.cruises.Passenger;
import com.rf.springsecurity.domain.orders.Order;
import com.rf.springsecurity.domain.ports.Excursion;
import com.rf.springsecurity.dto.OrderDTO;
import com.rf.springsecurity.exceptions.NotEnoughMoney;
import com.rf.springsecurity.repository.OrderRepository;
import com.rf.springsecurity.repository.PassengerRepository;
import com.rf.springsecurity.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// TODO clear
@Service
public class BuyCruiseService {

    private OrderRepository orderRepository;
    private PassengerRepository passengerRepository;
    private final TicketRepository ticketRepository;
    private final BalanceService balanceService;


    @Autowired
    public BuyCruiseService(OrderRepository orderRepository, PassengerRepository passengerRepository, CruiseService cruiseService, BalanceService balanceService, TicketRepository ticketRepository) {
        this.orderRepository = orderRepository;
        this.passengerRepository = passengerRepository;
        this.balanceService = balanceService;
        this.ticketRepository = ticketRepository;
    }


    public void buy(OrderDTO orderDTO) throws Exception {
        addNewOrder(orderDTO);
        addPassenger(orderDTO);
        balanceService.subBalance(getTotalCruisePrice(orderDTO));
    }

    private void addNewOrder(OrderDTO orderDTO) throws Exception {
        orderRepository.save(
                Order.builder()
                    .cruise(orderDTO.getCruise())
                    .user(orderDTO.getUser())
                    .ticket(ticketRepository.findById(orderDTO.getTicket().getId()).orElseThrow(Exception::new))
                    .build());
    }

    private void addPassenger(OrderDTO orderDTO) throws Exception {
        passengerRepository.save(
                Passenger.builder()
                    .firstName(orderDTO.getFirstName())
                    .secondName(orderDTO.getSecondName())
                    .ticket(ticketRepository.findById(orderDTO.getTicket().getId()).orElseThrow(Exception::new))
                    .ship(orderDTO.getCruise().getShip())
                    .build());
    }

    private long getTotalCruisePrice(OrderDTO orderDTO){
        return orderDTO.getTicket().getPrice() + getExcursionsTotalPrice(orderDTO);
    }

    private long getExcursionsTotalPrice(OrderDTO orderDTO){
        return orderDTO.getExcursions().stream()
                .filter((excursion -> excursion.getExcursionName() != null))
                .mapToLong(Excursion::getPrice)
                .sum();

    }
//    //todo replace orElse
//    private Ticket getTicket(OrderDTO orderDTO) {
//        return orderDTO.getCruise().getTickets().stream()
//                .filter((ticket) -> orderDTO.getTicket_id() == ticket.getId()).findAny().orElse(null);
//    }


}
