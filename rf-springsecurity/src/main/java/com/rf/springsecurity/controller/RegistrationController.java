package com.rf.springsecurity.controller;

import com.rf.springsecurity.domain.Role;
import com.rf.springsecurity.domain.User;
import com.rf.springsecurity.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class RegistrationController {

    private UserService userService;
    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User user, Model model) {
        System.out.println(user.toString());
        user.setActive(true);
        user.setRoles(Role.ROLE_USER);
        try{
            userService.saveNewUser(user);
        }catch (Exception ex){
            log.info(user.getLogin() + " login is already exist");
            model.addAttribute("message", "login is already exist");
            return "registration";
        }
        return "redirect:/login";
    }
}
