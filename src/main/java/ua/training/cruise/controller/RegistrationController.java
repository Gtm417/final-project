package ua.training.cruise.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ua.training.cruise.entity.user.Role;
import ua.training.cruise.entity.user.User;
import ua.training.cruise.exception.DataBaseDuplicateConstraint;
import ua.training.cruise.service.UserService;

@Slf4j
@Controller
public class RegistrationController {

    private UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;

    }

    @GetMapping("/registration")
    public ModelAndView registration(@RequestParam(value = "error", required = false) String error) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("error", error != null);
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @PostMapping("/registration")
    public String addUser(User user) throws DataBaseDuplicateConstraint {
        user.setActive(true);
        user.setRoles(Role.ROLE_USER);
        userService.saveNewUser(user);

        return "redirect:/login";
    }

    @ExceptionHandler(DataBaseDuplicateConstraint.class)
    public String duplicateConstraintHandling(DataBaseDuplicateConstraint ex) {
        log.info(ex.getMessage());
        return "redirect:/registration?error";
    }
}


