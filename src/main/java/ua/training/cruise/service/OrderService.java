package ua.training.cruise.service;

import lombok.NonNull;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.training.cruise.dto.OrderDTO;
import ua.training.cruise.entity.cruise.Cruise;
import ua.training.cruise.entity.cruise.Ship;
import ua.training.cruise.entity.order.Order;
import ua.training.cruise.entity.user.User;
import ua.training.cruise.exception.EntityNotFound;
import ua.training.cruise.exception.NotEnoughMoney;
import ua.training.cruise.exception.OrderSaveException;
import ua.training.cruise.repository.OrderRepository;
import ua.training.cruise.repository.ShipRepository;
import ua.training.cruise.repository.UserRepository;
import ua.training.cruise.service.mapper.OrderMapper;

import java.util.List;

@Log4j
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ShipRepository shipRepository;
    private final OrderMapper mapper;


    @Autowired
    public OrderService(OrderRepository orderRepository, UserRepository userRepository, ShipRepository shipRepository, OrderMapper mapper) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.shipRepository = shipRepository;
        this.mapper = mapper;
    }

    public List<Order> findAllOrdersByCruise(Cruise cruise) {
        return orderRepository.findAllByCruise(cruise);
    }

    public Page<Order> findAllOrdersByUser(User user, Pageable pageable) {
        return orderRepository.findAllByUser(user, pageable);
    }

    public boolean buyCruise(@NonNull Order order, @NonNull Cruise cruise, @NonNull User user) {
        order.setCruise(cruise);
        order.setUser(user);

        try {
            buyInTransaction(order);
        } catch (Exception e) {
            throw new OrderSaveException("Transaction rollback. Something went wrong during the addition of the order:", e);
        }
        return true;
    }

    @Transactional
    public void buyInTransaction(@NonNull Order order) {
        Ship ship = shipRepository.findById(order.getCruise().getShip().getId())
                .orElseThrow(() -> new EntityNotFound("Ship not found with id: " + order.getCruise().getShip().getId()));
        ship.setCurrentAmountOfPassenger(incrementPassengerAmount(ship));
        User user = userRepository.findById(order.getUser().getId())
                .orElseThrow(() -> new EntityNotFound("User not found with id: " + order.getUser().getId()));
        order.getCruise().setShip(ship);
        order.setUser(setUserBalance(user, order.getOrderPrice()));
        orderRepository.save(order);
    }

    private int incrementPassengerAmount(Ship dbShip) {
        return dbShip.getCurrentAmountOfPassenger() + 1;
    }

    private User setUserBalance(User user, long orderSum) {
        user.setBalance(calcBalance(user, orderSum));
        return user;
    }

    private long calcBalance(User user, long orderSum) {
        long totalBalance = user.getBalance() - orderSum;
        if (totalBalance < 0) {
            throw new NotEnoughMoney("Not Enough Money " + user.getBalance());
        }
        return totalBalance;
    }


    public Order getEntityFromDTO(OrderDTO orderDTO) {
        return mapper.mapToEntity(orderDTO);
    }
}
