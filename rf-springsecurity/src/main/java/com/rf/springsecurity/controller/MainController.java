package com.rf.springsecurity.controller;


import com.rf.springsecurity.services.UserAuthenticationService;
import com.rf.springsecurity.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static java.util.stream.Collectors.joining;

@Controller
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true)
public class MainController {

    private UserAuthenticationService userAuthenticationService;
    private UserService userService;

    @Autowired
    public MainController(UserService userService,UserAuthenticationService userAuthenticationService) {
        this.userService = userService;
        this.userAuthenticationService = userAuthenticationService;
    }

    @ModelAttribute
    public void getUserName(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("username", user.getUsername());
    }

    @RequestMapping("/")
    public String getMainPage(Model model) {

        UserDetails user = userAuthenticationService.getAuthenticatedUser();

        model.addAttribute("login", user.getUsername());
        model.addAttribute("role", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(joining(",")));
        return "hello";
    }
    @GetMapping("/main")
    public String getMainPage(){
        return "main";

    }

    @GetMapping("/all_users")
    @PreAuthorize("hasRole('ADMIN')")
    public String getAllUsers(Model model){
        model.addAttribute("users", userService.getAllUsers().getUsers());
        return "users";
    }

    @GetMapping("/redirectToBalanceReplenish")
    public String  redirectToBalance(){
        //String login = userAuthenticationService.getAuthenticatedUser().getUsername();
        return "redirect:/balance";
    }

    @GetMapping("my-error-page")
    public String getErrorPage(){
        return "error-page";
    }
}
