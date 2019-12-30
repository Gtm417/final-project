package com.rf.springsecurity.controller;

import com.rf.springsecurity.dto.BalanceUserDTO;
import com.rf.springsecurity.exceptions.NotAuthenticatedRequest;
import com.rf.springsecurity.services.BalanceService;
import com.rf.springsecurity.services.UserAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller
public class BalanceController {

    BalanceService balanceService;
    UserAuthenticationService userAuthenticationService;

    @Autowired
    public BalanceController(BalanceService balanceService,UserAuthenticationService userAuthenticationService) {
        this.balanceService = balanceService;
        this.userAuthenticationService = userAuthenticationService;
    }

    @GetMapping("/balance")
    public String balanceReplenish(Model model) {
        String login = userAuthenticationService.getAuthenticatedUser().getUsername();
        BalanceUserDTO userBalance = balanceService.getUserBalance(login);
        model.addAttribute("balance", userBalance.getBalance());
        model.addAttribute("login", login);
        return "balance";
    }

    //TODO ловить не правильный ввод в форму

    @PostMapping("/replenishment")
    public String replenishment(@ModelAttribute("balance") long balance){
        try{
            balanceService.updateBalance(userAuthenticationService.getAuthenticatedUser().getUsername(), balance);
        }catch (Exception e){
            //TODO редирект с ошибкой
            return "redirect:/balance";
        }
        return "redirect:/balance";
    }


    //TODO на форме клиент вводит число  и его  баланс пополняется на это число
    // Метод ПОСТ
    // контролер который это обрабатывает должен иметь BalanceService
    // получить данные с формы и сделать апдейт базы по логину

}
