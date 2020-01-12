package com.rf.springsecurity.controller;

import com.rf.springsecurity.dto.BalanceUserDTO;
import com.rf.springsecurity.services.BalanceService;
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

    @Autowired
    public BalanceController(BalanceService balanceService) {
        this.balanceService = balanceService;
    }

    @ModelAttribute
    public void getAuthUserBalance(@AuthenticationPrincipal User user,
                                   Model model){
        model.addAttribute("login", user.getUsername());
        model.addAttribute("balance",balanceService.getUserBalance().getBalance());
        System.err.println("?????????????????????????????");
    }

    @GetMapping("/balance")
    public String balanceReplenish(BalanceUserDTO balanceDTO, Model model) {
            model.addAttribute("balanceDTO", balanceDTO);
        return "balance";
    }

    @PostMapping("/replenishment")
    public String replenishment(@Valid @ModelAttribute("balanceDTO")BalanceUserDTO balanceDTO,
                                BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "balance";
        }
        balanceService.addBalance(balanceDTO.getBalance());
        return "redirect:/balance";
    }
}
