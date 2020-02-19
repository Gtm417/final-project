package ua.training.cruise.controller;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import ua.training.cruise.entity.cruise.Cruise;
import ua.training.cruise.entity.cruise.Ticket;
import ua.training.cruise.entity.order.Order;
import ua.training.cruise.entity.port.Excursion;
import ua.training.cruise.service.ExcursionService;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.not;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static ua.training.cruise.controller.SessionAttributeConstants.SESSION_CRUISE;
import static ua.training.cruise.controller.SessionAttributeConstants.SESSION_ORDER;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ExcursionController.class)
public class ExcursionControllerTest {
    public static final Excursion EXCURSION = Excursion.builder().id(1L).price(100L).build();
    Model model = new ExtendedModelMap();
    HttpSession session = new MockHttpSession();

    @Autowired
    ExcursionController controller;

    @MockBean
    ExcursionService excursionService;

    @Test
    public void addExcursion() {
        session.setAttribute(SESSION_ORDER, Order.builder().excursions(new HashSet<>()).build());

        when(excursionService.findById(anyLong())).thenReturn(EXCURSION);
        String actual = controller.addExcursion(1L, session);

        verify(excursionService, times(1)).findById(anyLong());
        Assert.assertThat(((Order) session.getAttribute(SESSION_ORDER)).getExcursions(), Matchers.contains(EXCURSION));
        Assert.assertEquals("redirect:/user/cruise/buy-submit", actual);
    }

    @Test
    public void removeExcursion() {
        Set<Excursion> excursions = new HashSet<>();
        excursions.add(EXCURSION);
        session.setAttribute(SESSION_ORDER, Order.builder().excursions(excursions).build());

        when(excursionService.findById(anyLong())).thenReturn(EXCURSION);
        String actual = controller.removeExcursion(1L, session);

        verify(excursionService, times(1)).findById(anyLong());
        Assert.assertThat(((Order) session.getAttribute(SESSION_ORDER)).getExcursions(), not(Matchers.contains(EXCURSION)));
        Assert.assertEquals("redirect:/user/cruise/buy-submit", actual);
    }

    @Test
    public void submitBuyPage() {
        session.setAttribute(SESSION_ORDER, buildOrderWithSingletonList());
        session.setAttribute(SESSION_CRUISE, Cruise.builder().id(1L).build());
        long cruiseSessionId = ((Cruise) session.getAttribute(SESSION_CRUISE)).getId();

        when(excursionService.getAllExcursionsByCruiseId(cruiseSessionId)).thenReturn(Collections.singletonList(EXCURSION));

        String actual = controller.submitBuyPage(model, session);

        Assert.assertNotNull(model.getAttribute("excursions"));
        Assert.assertEquals("submit-form", actual);
    }

    private Order buildOrderWithSingletonList() {
        return Order.builder().orderPrice(1000L).ticket(buildTicket()).excursions(Collections.singleton(EXCURSION)).build();
    }

    private Ticket buildTicket() {
        return Ticket.builder().priceWithDiscount(100L).build();
    }
}