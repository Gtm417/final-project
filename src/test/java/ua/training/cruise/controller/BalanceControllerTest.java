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
import ua.training.cruise.dto.BalanceDTO;
import ua.training.cruise.entity.user.User;
import ua.training.cruise.service.UserService;

import javax.servlet.http.HttpSession;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static ua.training.cruise.controller.SessionAttributeConstants.SESSION_USER;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = BalanceController.class)
public class BalanceControllerTest {
    Model model = new ExtendedModelMap();
    HttpSession session = new MockHttpSession();

    @Autowired
    BalanceController balanceController;

    @MockBean
    UserService userService;
    @MockBean
    BindingResult bindingResult;


    @Test
    public void balanceReplenish() {
        String actual = balanceController.balanceReplenish(model);

        Assert.assertNotNull(model.getAttribute("balanceDTO"));
        Assert.assertEquals("balance", actual);
    }

    @Test
    public void replenishmentIfBindingResultHasErrors() {
        when(bindingResult.hasErrors()).thenReturn(true);

        String actual = balanceController.balanceReplenish(model);

        verify(userService, times(0)).addBalance(any(User.class), any(BalanceDTO.class));
        Assert.assertEquals("balance", actual);
    }

    @Test
    public void replenishmentIfBindingResultWithoutErrors() {
        session.setAttribute(SESSION_USER, new User());

        when(bindingResult.hasErrors()).thenReturn(false);
        when(userService.addBalance(any(User.class), any(BalanceDTO.class))).thenReturn(new User());

        String actual = balanceController.replenishment(new BalanceDTO(1000L), bindingResult, session);

        verify(userService, times(1)).addBalance(any(User.class), any(BalanceDTO.class));
        Assert.assertEquals("redirect:/balance", actual);
    }
}