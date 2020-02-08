package com.rf.springsecurity.controller;

import com.rf.springsecurity.entity.user.Role;
import com.rf.springsecurity.entity.user.User;
import com.rf.springsecurity.exception.DataBaseDuplicateConstraint;
import com.rf.springsecurity.services.UserAuthenticationService;
import com.rf.springsecurity.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
public class RegistrationController {

    private UserService userService;
    private UserAuthenticationService userAuthenticationService;

    @Autowired
    public RegistrationController(UserService userService, UserAuthenticationService userAuthenticationService) {
        this.userService = userService;
        this.userAuthenticationService = userAuthenticationService;
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

        userAuthenticationService.autoLogin(user.getLogin(), user.getPassword());

        return "redirect:/user";
    }

    @ExceptionHandler(DataBaseDuplicateConstraint.class)
    public String duplicateConstraintHandling(DataBaseDuplicateConstraint ex){
        log.info(ex.getMessage());
        return "redirect:/registration?error";
    }
}


