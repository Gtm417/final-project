package ua.training.cruise.controller;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import ua.training.cruise.dto.RegistrationDTO;
import ua.training.cruise.entity.user.User;
import ua.training.cruise.exception.DataBaseDuplicateConstraint;
import ua.training.cruise.service.UserService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = RegistrationController.class)
public class RegistrationControllerTest {
    @Rule
    public ExpectedException exception;
    Model model = new ExtendedModelMap();
    @Autowired
    RegistrationController controller;
    @MockBean
    UserService userService;
    @MockBean
    BindingResult bindingResult;

    @Test
    public void registrationWithParams() {
        String actual = controller.registration("error", model);


        Assert.assertEquals(model.getAttribute("error"), true);
        Assert.assertNotNull(model.getAttribute("registrationDTO"));
        Assert.assertEquals("registration", actual);
    }

    @Test
    public void registrationWithNullParams() {
        String actual = controller.registration(null, model);


        Assert.assertEquals(model.getAttribute("error"), false);
        Assert.assertNotNull(model.getAttribute("registrationDTO"));
        Assert.assertEquals("registration", actual);
    }

    @Test
    public void addUserWhenBindingResultWithoutErrors() throws DataBaseDuplicateConstraint {
        RegistrationDTO dto = new RegistrationDTO("test", "test");
        when(userService.saveNewUser(any(RegistrationDTO.class))).thenReturn(new User());
        when(bindingResult.hasErrors()).thenReturn(false);

        String actual = controller.addUser(dto, bindingResult);

        verify(userService, times(1)).saveNewUser(dto);
        Assert.assertEquals("redirect:/login", actual);
    }

    @Test
    public void addUserWhenBindingResultHasErrors() throws DataBaseDuplicateConstraint {
        RegistrationDTO dto = new RegistrationDTO("test", "test");
        when(bindingResult.hasErrors()).thenReturn(true);

        String actual = controller.addUser(dto, bindingResult);

        verify(userService, times(0)).saveNewUser(dto);
        Assert.assertEquals("registration", actual);
    }

    @Test(expected = DataBaseDuplicateConstraint.class)
    public void addUserThrowException() throws DataBaseDuplicateConstraint {
        RegistrationDTO dto = new RegistrationDTO("test", "test");
        when(bindingResult.hasErrors()).thenReturn(false);
        when(userService.saveNewUser(any(RegistrationDTO.class))).thenThrow(DataBaseDuplicateConstraint.class);

        controller.addUser(dto, bindingResult);

        verify(userService, times(0)).saveNewUser(dto);
    }
}