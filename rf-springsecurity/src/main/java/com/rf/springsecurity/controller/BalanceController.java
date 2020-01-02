package com.rf.springsecurity.controller;

import com.rf.springsecurity.dto.BalanceUserDTO;
import com.rf.springsecurity.exceptions.NotAuthenticatedRequest;
import com.rf.springsecurity.services.BalanceService;
import com.rf.springsecurity.services.UserAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller
public class BalanceController {

    private BalanceService balanceService;
    private UserAuthenticationService userAuthenticationService;

    private Validator balanceValidator;

    @Autowired
    public BalanceController(BalanceService balanceService, UserAuthenticationService userAuthenticationService, @Qualifier("balanceValidator") Validator balanceValidator) {
        this.balanceService = balanceService;
        this.userAuthenticationService = userAuthenticationService;
        this.balanceValidator = balanceValidator;
    }

    @GetMapping("/balance")
    public String balanceReplenish(Model model,
                                   @RequestParam(value = "error", required = false) String error) {

        String login = userAuthenticationService.getAuthenticatedUser().getUsername();
        BalanceUserDTO userBalance = balanceService.getUserBalance(login);
        model.addAttribute("error", error != null);
        model.addAttribute("balance", userBalance.getBalance());
        model.addAttribute("login", login);
        return "balance";
    }

    @PostMapping("/replenishment")
    public String replenishment(@Valid @ModelAttribute("balance") Long balance,
                                BindingResult bindingResult){
//        balanceValidator.validate(balance,bindingResult);
//        if(bindingResult.hasErrors()){
//            return "balance";
//        }

        try{
            if(balance < 0){
                return "redirect:/balance?error";
            }
            balanceService.updateBalance(userAuthenticationService.getAuthenticatedUser().getUsername(), balance);
        }catch (Exception e){
            return "redirect:/balance";
        }
        return "redirect:/balance";
    }

    @ExceptionHandler({NumberFormatException.class, NoSuchMethodException.class})
    public String exceptionCatch(){
        return "redirect:/balance?error";
    }
}
