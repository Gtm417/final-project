package ua.training.cruise.controller;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;
import ua.training.cruise.dto.OrderDTO;
import ua.training.cruise.entity.cruise.Cruise;
import ua.training.cruise.entity.cruise.Ticket;
import ua.training.cruise.entity.order.Order;
import ua.training.cruise.entity.user.Role;
import ua.training.cruise.entity.user.User;
import ua.training.cruise.service.CruiseService;
import ua.training.cruise.service.OrderService;
import ua.training.cruise.service.TicketService;
import ua.training.cruise.service.UserService;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static ua.training.cruise.controller.SessionAttributeConstants.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = UserController.class)
public class UserControllerTest {

    Model model = new ExtendedModelMap();
    HttpSession session = new MockHttpSession();

    @Autowired
    UserController controller;

    @MockBean
    UserService userService;
    @MockBean
    CruiseService cruiseService;
    @MockBean
    OrderService orderService;
    @MockBean
    TicketService ticketService;
    @MockBean
    BindingResult bindingResult;

    @Test
    public void user() {
        org.springframework.security.core.userdetails.User userDetails =
                new org.springframework.security.core.userdetails.User("test", "test", Collections.singletonList(Role.ROLE_USER));

        when(userService.getUserByLogin(userDetails.getUsername())).thenReturn(new User());

        String actual = controller.getUserPage(session, userDetails);

        verify(userService, times(1)).getUserByLogin(anyString());
        Assert.assertNotNull(session.getAttribute(SESSION_USER));
        Assert.assertEquals("user-page", actual);
    }

    @Test
    public void getMainPage() {
        session.setAttribute(SESSION_CRUISE, new Cruise());
        session.setAttribute(SESSION_ORDER, new Order());

        when(cruiseService.getAllCruises()).thenReturn(Collections.singletonList(new Cruise()));
        String actual = controller.getMainPage(model, session);

        verify(cruiseService, times(1)).getAllCruises();
        Assert.assertNotNull(model.getAttribute("cruises"));
        Assert.assertEquals("main", actual);
        Assert.assertNull(session.getAttribute(SESSION_CRUISE));
        Assert.assertNull(session.getAttribute(SESSION_ORDER));

    }


    @Test
    public void getCruisePageWithNoPlace() {

        when(cruiseService.findCruiseById(anyLong())).thenReturn(Cruise.builder().ports(new ArrayList<>()).build());

        String actual = controller.getCruisePage(1L, true, model, session);

        verify(cruiseService, times(1)).findCruiseById(1L);
        Assert.assertNotNull(session.getAttribute(SESSION_CRUISE));
        Assert.assertEquals(true, model.getAttribute("noPlace"));
        Assert.assertNotNull(model.getAttribute("cruise"));
        Assert.assertNotNull(model.getAttribute("ports"));
        Assert.assertEquals("cruise", actual);
    }

    @Test
    public void getCruisePage() {

        when(cruiseService.findCruiseById(anyLong())).thenReturn(Cruise.builder().ports(new ArrayList<>()).build());

        String actual = controller.getCruisePage(1L, false, model, session);

        verify(cruiseService, times(1)).findCruiseById(1L);
        Assert.assertNotNull(session.getAttribute(SESSION_CRUISE));
        Assert.assertEquals(false, model.getAttribute("noPlace"));
        Assert.assertNotNull(model.getAttribute("cruise"));
        Assert.assertNotNull(model.getAttribute("ports"));
        Assert.assertEquals("cruise", actual);
    }

    @Test
    public void getCruiseBuyForm() {
        session.setAttribute(SESSION_CRUISE, new Cruise());

        when(ticketService.showAllTicketsForCruise(any(Cruise.class))).thenReturn(Collections.singletonList(new Ticket()));

        String actual = controller.getCruiseBuyForm(model, session);

        verify(ticketService, times(1)).showAllTicketsForCruise(any(Cruise.class));

        Assert.assertNotNull(model.getAttribute("tickets"));
        Assert.assertNotNull(model.getAttribute("order"));
        Assert.assertEquals("buy-cruise", actual);
    }

    @Test
    public void buyCruiseWhenBindingResultWithoutErrors() {
        OrderDTO dto = new OrderDTO("test", "test", new Ticket());
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();

        when(bindingResult.hasErrors()).thenReturn(false);
        when(orderService.getEntityFromDTO(dto)).thenReturn(new Order());
        String actual = controller.buyCruise(dto, bindingResult, redirectAttributes, session);

        verify(orderService, times(1)).getEntityFromDTO(any(OrderDTO.class));
        Assert.assertNotNull(session.getAttribute(SESSION_ORDER));
        Assert.assertEquals("redirect:/user/cruise/buy-submit", actual);
    }

    @Test
    public void buyCruiseWhenBindingResultHasErrors() {
        OrderDTO dto = new OrderDTO("test", "test", new Ticket());
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();

        when(bindingResult.hasErrors()).thenReturn(true);
        String actual = controller.buyCruise(dto, bindingResult, redirectAttributes, session);

        verify(orderService, times(0)).getEntityFromDTO(any(OrderDTO.class));
        Assert.assertTrue(redirectAttributes.getFlashAttributes().containsKey("fieldErrors"));
        Assert.assertNull(session.getAttribute(SESSION_ORDER));
        Assert.assertEquals("redirect:/user/cruise/buy", actual);
    }

    @Test
    public void submitBuy() {
        session.setAttribute(SESSION_CRUISE, new Cruise());
        session.setAttribute(SESSION_USER, new User());
        session.setAttribute(SESSION_ORDER, new Order());

        when(orderService.buyCruise(any(Order.class), any(Cruise.class), any(User.class))).thenReturn(true);
        String actual = controller.submitBuy(session);

        verify(orderService, times(1)).buyCruise(any(Order.class), any(Cruise.class), any(User.class));
        Assert.assertEquals("redirect:/user/success-buy", actual);
    }

    @Test
    public void successBuy() {
        session.setAttribute(SESSION_ORDER, new Order());
        session.setAttribute(SESSION_CRUISE, new Cruise());
        String actual = controller.successBuy(session);

        Assert.assertNull(session.getAttribute(SESSION_ORDER));
        Assert.assertNull(session.getAttribute(SESSION_CRUISE));
        Assert.assertEquals("success-buy", actual);
    }

    @Test
    public void getAllOrders() {
        session.setAttribute(SESSION_USER, new User());
        Pageable pageable = PageRequest.of(1, 1);
        when(orderService.findAllOrdersByUser(any(User.class), any(Pageable.class))).thenReturn(new PageImpl<>(Collections.singletonList(new Order())));
        String actual = controller.getAllOrders(session, model, pageable);

        Assert.assertNotNull(model.getAttribute("page"));
        Assert.assertEquals("orders", actual);
    }
}