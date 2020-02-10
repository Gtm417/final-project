package ua.training.cruise.service;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Page<Order> findAllOrdersByUser(User user, Pageable pageable) {
        return orderRepository.findAllByUser(user, pageable);
    }

    public boolean buyCruise(@NonNull Order order, @NonNull Cruise cruise, @NonNull User user) throws NotEnoughMoney, NoPlaceOnShip {
        order.setCruise(cruise);
        order.setUser(user);
        subBalance(user, order.getOrderPrice());

        buyDbChanges(order, user, cruise.getShip());
        return true;
    }

    public int checkShipCapacity(@NonNull Ship ship) throws NoPlaceOnShip {
        if (ship.getCurrentAmountOfPassenger() + 1 > ship.getMaxAmountOfPassenger()) {
            throw new NoPlaceOnShip("No place on cruise with id ", ship.getId());
        }
        return ship.getCurrentAmountOfPassenger() + 1;
    }

    private void setAmountShipCapacity(@NonNull Ship ship) throws NoPlaceOnShip {
        ship.setCurrentAmountOfPassenger(checkShipCapacity(ship));
    }

    @Transactional
    public void buyDbChanges(@NonNull Order order, @NonNull User user, Ship ship) throws NoPlaceOnShip {
        setAmountShipCapacity(ship);
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
