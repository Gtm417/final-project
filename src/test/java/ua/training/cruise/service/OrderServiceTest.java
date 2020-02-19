package ua.training.cruise.service;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ua.training.cruise.entity.cruise.Cruise;
import ua.training.cruise.entity.cruise.Ship;
import ua.training.cruise.entity.order.Order;
import ua.training.cruise.entity.user.Role;
import ua.training.cruise.entity.user.User;
import ua.training.cruise.exception.EntityNotFound;
import ua.training.cruise.exception.OrderSaveException;
import ua.training.cruise.repository.OrderRepository;
import ua.training.cruise.repository.ShipRepository;
import ua.training.cruise.repository.UserRepository;
import ua.training.cruise.service.mapper.OrderMapper;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = OrderService.class)
public class OrderServiceTest {

    private final static Ship SHIP = buildShip();
    private static final User USER = buildUser();
    private static final Cruise CRUISE = buildCruise();
    private final static Order ORDER = buildOrder();
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Autowired
    OrderService service;

    @MockBean
    OrderRepository orderRepository;

    @MockBean
    UserRepository userRepository;

    @MockBean
    ShipRepository shipRepository;

    @MockBean
    OrderMapper mapper;

    private static Ship buildShip() {

        return Ship.builder().id(1L).shipName("ship").currentAmountOfPassenger(50).maxAmountOfPassenger(100).build();
    }

    private static User buildUser() {
        return User.builder()
                .id(1L)
                .login("login")
                .password("pass")
                .roles(Role.ROLE_USER)
                .balance(10000L)
                .build();
    }

    private static Order buildOrder() {
        return Order.builder()
                .firstName("test")
                .secondName("test")
                .orderPrice(1000)
                .user(USER)
                .cruise(OrderServiceTest.buildCruise())
                .build();
    }

    private static Cruise buildCruise() {
        return Cruise.builder().ship(SHIP).build();
    }


    @Test
    public void findAllOrdersByCruise() {
        List<Order> list = Collections.singletonList(ORDER);

        when(orderRepository.findAllByCruise(any(Cruise.class))).thenReturn(list);
        List<Order> actual = service.findAllOrdersByCruise(new Cruise());
        verify(orderRepository, times(1)).findAllByCruise(any(Cruise.class));

        Assert.assertEquals(list, actual);
    }

    @Test
    public void findAllOrdersByUser() {
        service.findAllOrdersByUser(new User(), PageRequest.of(1, 1));
        verify(orderRepository, times(1)).findAllByUser(any(User.class), any(Pageable.class));
    }

    @Test
    public void buyCruise() {
        when(shipRepository.findById(SHIP.getId())).thenReturn(Optional.of(SHIP));
        when(userRepository.findById(USER.getId())).thenReturn(Optional.of(USER));

        boolean actual = service.buyCruise(ORDER, CRUISE, USER);

        verify(shipRepository, times(1)).findById(SHIP.getId());
        verify(userRepository, times(1)).findById(USER.getId());
        verify(orderRepository, times(1)).save(any(Order.class));

        Assert.assertTrue(actual);
    }

    @Test(expected = OrderSaveException.class)
    public void buyCruiseIfSaveThrowException() {
        when(shipRepository.findById(SHIP.getId())).thenReturn(Optional.of(SHIP));
        when(userRepository.findById(USER.getId())).thenReturn(Optional.of(USER));
        when(orderRepository.save(any(Order.class))).thenThrow(new RuntimeException());

        service.buyCruise(ORDER, CRUISE, USER);

    }

    @Test
    public void buyDbChanges() {
        int shipPassengerAmount = SHIP.getCurrentAmountOfPassenger();
        long userBalance = USER.getBalance();
        when(shipRepository.findById(SHIP.getId())).thenReturn(Optional.of(SHIP));
        when(userRepository.findById(USER.getId())).thenReturn(Optional.of(USER));

        service.buyDbChanges(ORDER);

        verify(shipRepository, times(1)).findById(SHIP.getId());
        verify(userRepository, times(1)).findById(USER.getId());
        verify(orderRepository, times(1)).save(any(Order.class));


        Assert.assertThat(ORDER.getCruise().getShip().getCurrentAmountOfPassenger(), is(shipPassengerAmount + 1));
        Assert.assertThat(ORDER.getUser().getBalance(), is(userBalance - ORDER.getOrderPrice()));
    }

    @Test(expected = EntityNotFound.class)
    public void buyDbChangesWhenUserEntityNotFound() {
        when(shipRepository.findById(anyLong())).thenReturn(Optional.of(SHIP));
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        service.buyDbChanges(ORDER);
    }

    @Test(expected = EntityNotFound.class)
    public void buyDbChangesWhenShipEntityNotFound() {
        when(shipRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(USER));

        service.buyDbChanges(ORDER);
    }

}