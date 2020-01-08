package com.rf.springsecurity.controller;


import com.rf.springsecurity.exceptions.UnsupportedCruiseName;
import com.rf.springsecurity.exceptions.UnsupportedUserName;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UnsupportedCruiseName.class)
    public String  handlingUnsupportedCruiseName(UnsupportedCruiseName ex, Model model){
        model.addAttribute("error", ex.getMessage());
        log.error("Cruise not found: " + ex.getMessage());
        return "redirect:/main";
    }

    @ExceptionHandler(UnsupportedUserName.class)
    public String handlingUnsupportedUserName(Model model, UnsupportedUserName ex){
        model.addAttribute("error", ex.getMessage());
        log.info(ex.getMessage());
        return "redirect:/login";
    }
    //TODO EXception NotEnoughMoney Handling



}
