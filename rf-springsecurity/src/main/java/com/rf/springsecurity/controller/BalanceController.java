package com.rf.springsecurity.controller;

import com.rf.springsecurity.dto.BalanceUserDTO;
import com.rf.springsecurity.services.BalanceService;
import com.rf.springsecurity.services.UserAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller

public class BalanceController {

    private BalanceService balanceService;
    private UserAuthenticationService userAuthenticationService;

    @Autowired
    public BalanceController(BalanceService balanceService, UserAuthenticationService userAuthenticationService) {
        this.balanceService = balanceService;
        this.userAuthenticationService = userAuthenticationService;
    }

    @ModelAttribute
    public void getAuthUserBalance(@AuthenticationPrincipal User user,
                                   Model model){
        model.addAttribute("login", user.getUsername());
        model.addAttribute("balance",balanceService.getUserBalance(user.getUsername()).getBalance());
    }

    @GetMapping("/balance")
    public String balanceReplenish(BalanceUserDTO balanceDTO,
                                   Model model) {
        model.addAttribute("balanceDTO", balanceDTO);
        return "balance";
    }

    @PostMapping("/replenishment")
    public String replenishment(@Valid @ModelAttribute("balanceDTO")BalanceUserDTO balanceDTO,
                                BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "balance";
        }
        try{
            balanceService.updateBalance(userAuthenticationService.getAuthenticatedUserDetails().getUsername(), balanceDTO.getBalance());
        }catch (Exception e){
            return "redirect:/balance";
        }
        return "redirect:/balance";
    }
}
