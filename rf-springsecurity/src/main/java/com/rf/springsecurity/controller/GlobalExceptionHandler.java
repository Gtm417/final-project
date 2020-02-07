package com.rf.springsecurity.controller;


import com.rf.springsecurity.exception.NotEnoughMoney;
import com.rf.springsecurity.exception.UnsupportedCruise;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    //TODO redirect to NOT FOUND 404, Убрать model.attribute error
    @ExceptionHandler(UnsupportedCruise.class)
    public String  handlingUnsupportedCruiseName(UnsupportedCruise ex){
        log.error("Cruise not found: " + ex.getMessage());
        return "404";
    }

    @ExceptionHandler(NotEnoughMoney.class)
    public String handlingNotEnoughMoney(Model model, NotEnoughMoney ex){
        model.addAttribute("notEnoughMoney", true);
        log.info(ex.getMessage());
        return "not-enough-money";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handling(){
        return "404";
    }


}
