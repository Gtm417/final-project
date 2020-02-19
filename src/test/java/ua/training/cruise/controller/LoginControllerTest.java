package ua.training.cruise.controller;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = LoginController.class)
public class LoginControllerTest {
    Model model = new ExtendedModelMap();

    @Autowired
    LoginController controller;


    @Test
    public void getLoginWithRequestParams() {
        String actual = controller.getLogin("error", "logout", model);

        Assert.assertEquals(model.getAttribute("error"), true);
        Assert.assertEquals(model.getAttribute("logout"), true);
        Assert.assertEquals("login", actual);
    }


    @Test
    public void getLoginWithNullRequestParams() {
        String actual = controller.getLogin(null, null, model);

        Assert.assertEquals(model.getAttribute("error"), false);
        Assert.assertEquals(model.getAttribute("logout"), false);
        Assert.assertEquals("login", actual);
    }
}