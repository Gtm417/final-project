package ua.training.cruise.controller;

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
import org.springframework.validation.BindingResult;
import ua.training.cruise.dto.CruiseDescriptionsDTO;
import ua.training.cruise.dto.TicketDTO;
import ua.training.cruise.entity.cruise.Cruise;
import ua.training.cruise.entity.cruise.Ticket;
import ua.training.cruise.exception.DataBaseDuplicateConstraint;
import ua.training.cruise.service.CruiseService;
import ua.training.cruise.service.OrderService;
import ua.training.cruise.service.TicketService;

import javax.servlet.http.HttpSession;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static ua.training.cruise.controller.SessionAttributeConstants.SESSION_CRUISE;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = AdminController.class)
public class AdminControllerTest {

    Model model = new ExtendedModelMap();
    HttpSession session = new MockHttpSession();

    @Autowired
    AdminController adminController;

    @MockBean
    CruiseService cruiseService;
    @MockBean
    OrderService orderService;
    @MockBean
    TicketService ticketService;
    @MockBean
    BindingResult bindingResult;


    @Test
    public void getDescriptionEditPage() {

        String actual = adminController.getDescriptionEditPage(model);

        Assert.assertNotNull(model.getAttribute("descriptionDTO"));
        Assert.assertEquals("cruise/edit-description", actual);

    }

    @Test
    public void editCruiseDescriptionBindingResultWithoutErrors() {
        session.setAttribute(SESSION_CRUISE, new Cruise());

        when(cruiseService.changeCruiseDescription(any(Cruise.class), any(CruiseDescriptionsDTO.class))).thenReturn(new Cruise());
        when(bindingResult.hasErrors()).thenReturn(false);

        String actual = adminController.editCruiseDescription(new CruiseDescriptionsDTO("test", "test"), bindingResult, session);

        verify(cruiseService, times(1)).changeCruiseDescription(any(Cruise.class), any(CruiseDescriptionsDTO.class));
        Assert.assertEquals("cruise/edit-description", actual);
    }

    @Test
    public void editCruiseDescriptionBindingResultHasErrors() {

        when(bindingResult.hasErrors()).thenReturn(true);

        String actual = adminController.editCruiseDescription(new CruiseDescriptionsDTO("test", "test"), bindingResult, session);

        verify(cruiseService, times(0)).changeCruiseDescription(any(Cruise.class), any(CruiseDescriptionsDTO.class));
        Assert.assertEquals("cruise/edit-description", actual);
    }

    @Test
    public void getAddTicketPage() {
        String actual = adminController.getAddTicketPage("error", "success", model);

        Assert.assertNotNull(model.getAttribute("ticketDTO"));
        Assert.assertEquals(true, model.getAttribute("error"));
        Assert.assertEquals(true, model.getAttribute("success"));
        Assert.assertEquals("cruise/add-ticket", actual);
    }

    @Test
    public void getAddTicketPageIfRequestParamsNull() {
        String actual = adminController.getAddTicketPage(null, null, model);

        Assert.assertNotNull(model.getAttribute("ticketDTO"));
        Assert.assertEquals(false, model.getAttribute("error"));
        Assert.assertEquals(false, model.getAttribute("success"));
        Assert.assertEquals("cruise/add-ticket", actual);
    }

    @Test
    public void PostAddTicketPageIfBindingResultWithoutErrors() throws DataBaseDuplicateConstraint {
        session.setAttribute(SESSION_CRUISE, new Cruise());

        when(ticketService.addNewTicketToCruise(any(TicketDTO.class), any(Cruise.class))).thenReturn(new Ticket());
        when(bindingResult.hasErrors()).thenReturn(false);

        String actual = adminController.postAddTicketPage(new TicketDTO("test", 100L, 0), bindingResult, session);

        verify(ticketService, times(1)).addNewTicketToCruise(any(TicketDTO.class), any(Cruise.class));
        Assert.assertEquals("redirect:/cruise/edit/add/ticket?success", actual);
    }

    @Test
    public void PostAddTicketPageIfBindingResultHasErrors() throws DataBaseDuplicateConstraint {
        when(bindingResult.hasErrors()).thenReturn(true);

        String actual = adminController.postAddTicketPage(new TicketDTO("test", 100L, 0), bindingResult, session);

        verify(ticketService, times(0)).addNewTicketToCruise(any(TicketDTO.class), any(Cruise.class));
        Assert.assertEquals("cruise/add-ticket", actual);
    }


    @Test
    public void getAllPassengers() {
        session.setAttribute(SESSION_CRUISE, new Cruise());

        String actual = adminController.getAllPassengers(model, session);

        verify(orderService, times(1)).findAllOrdersByCruise(any(Cruise.class));
        Assert.assertNotNull(model.getAttribute("passengers"));
        Assert.assertEquals("cruise/cruise-passengers", actual);
    }


}