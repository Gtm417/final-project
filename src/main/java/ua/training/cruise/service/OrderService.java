package ua.training.cruise.service;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.training.cruise.entity.cruise.Cruise;
import ua.training.cruise.entity.cruise.Ship;
import ua.training.cruise.entity.order.Order;
import ua.training.cruise.entity.port.Excursion;
import ua.training.cruise.entity.user.User;
import ua.training.cruise.exception.NoPlaceOnShip;
import ua.training.cruise.exception.NotEnoughMoney;
import ua.training.cruise.repository.OrderRepository;
import ua.training.cruise.repository.ShipRepository;
import ua.training.cruise.repository.UserRepository;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ShipRepository shipRepository;


    @Autowired
    public OrderService(OrderRepository orderRepository, UserRepository userRepository, ShipRepository shipRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.shipRepository = shipRepository;
    }

    public List<Order> findAllOrdersByCruise(Cruise cruise) {
        return orderRepository.findAllByCruise(cruise);
    }

    public List<Order> findAllOrdersByUser(User user) {
        return orderRepository.findAllByUser(user);
    }

    public boolean buyCruise(@NonNull Order order, @NonNull Cruise cruise, @NonNull User user) throws NotEnoughMoney, NoPlaceOnShip {
        Ship ship = checkShipCapacity(cruise);
        order.setCruise(cruise);
        order.setUser(user);
        subBalance(user, order.getOrderPrice());

        buyDbChanges(order, user, ship);
        return true;
    }

    private Ship checkShipCapacity(@NonNull Cruise cruise) throws NoPlaceOnShip {
        int newPassengerAmount = cruise.getShip().getCurrentAmountOfPassenger() + 1;
        if (newPassengerAmount > cruise.getShip().getMaxAmountOfPassenger()) {
            throw new NoPlaceOnShip("No place on cruise with id ", cruise.getId());
        }
        cruise.getShip().setCurrentAmountOfPassenger(newPassengerAmount);
        return cruise.getShip();
    }

    @Transactional
    public void buyDbChanges(@NonNull Order order, @NonNull User user, Ship ship) {
        userRepository.save(user);
        orderRepository.save(order);
        shipRepository.save(ship);
    }


    private User subBalance(User user, long orderSum) throws NotEnoughMoney {
        long totalBalance = user.getBalance() - orderSum;
        if (totalBalance < 0) {
            throw new NotEnoughMoney("Not Enough Money " + user.getBalance());
        }
        user.setBalance(user.getBalance() - orderSum);
        return user;
    }

    public long getOrderTotalPrice(Order order) {
        long excursionsPrice = order.getExcursions().stream().mapToLong(Excursion::getPrice).sum();
        return order.getTicket().getPriceWithDiscount() + excursionsPrice;
    }

}
