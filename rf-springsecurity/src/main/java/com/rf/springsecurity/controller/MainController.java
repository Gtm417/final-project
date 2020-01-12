package com.rf.springsecurity.controller;


import com.rf.springsecurity.services.UserAuthenticationService;
import com.rf.springsecurity.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping("/")
    public String getHomePage(Model model) {
        //TODO maybe not needed getAuthUser() it can be done by @AuthenticationPrincipal User user
        UserDetails user = userAuthenticationService.getAuthenticatedUserDetails();
        model.addAttribute("role", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(joining(",")));
        return "hello";
    }

    @GetMapping("/all_users")
    @PreAuthorize("hasRole('ADMIN')")
    public String getAllUsers(Model model){
        model.addAttribute("users", userService.getAllUsers().getUsers());
        return "users";
    }

    //TODO not needed
    @GetMapping("/redirectToBalanceReplenish")
    public String  redirectToBalance(){
        return "redirect:/balance";
    }

    @GetMapping("my-error-page")
    public String getErrorPage(){
        return "error-page";
    }

    @ModelAttribute
    public void getUserName(@AuthenticationPrincipal User user, Model model){
        model.addAttribute("login", user.getUsername());
    }
}
