package com.rf.springsecurity.controller;


import com.rf.springsecurity.exceptions.UnsupportedCruiseName;
import com.rf.springsecurity.exceptions.UnsupportedUserName;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UnsupportedCruiseName.class)
    public void handlingUnsupportedCruiseName(){

    }

    @ExceptionHandler(UnsupportedUserName.class)
    public String handlingUnsupportedUserName(Model model, UnsupportedUserName ex){
        model.addAttribute("error", ex.getMessage());
        return "redirect:/login";
    }



}
