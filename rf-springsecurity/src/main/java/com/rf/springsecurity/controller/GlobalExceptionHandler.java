package com.rf.springsecurity.controller;


import com.rf.springsecurity.exceptions.NotEnoughMoney;
import com.rf.springsecurity.exceptions.UnsupportedCruiseName;
import com.rf.springsecurity.exceptions.UnsupportedUserName;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    //TODO redirect to NOT FOUND 404, Убрать model.attribute error
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

    @ExceptionHandler(NotEnoughMoney.class)
    public String handlingNotEnoughMoney(Model model, NotEnoughMoney ex){
        model.addAttribute("notEnoughMoney", true);
        log.info(ex.getMessage());
        return "not-enough-money";
    }
    //TODO Exception NotEnoughMoney Handling



}
