package ua.training.cruise.controller;


import lombok.extern.log4j.Log4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import ua.training.cruise.exception.*;

@Log4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UnsupportedCruise.class)
    public String handlingUnsupportedCruiseName(UnsupportedCruise ex) {
        log.info("Cruise not found: " + ex.getMessage());
        return "404";
    }

    @ExceptionHandler(NotEnoughMoney.class)
    public String handlingNotEnoughMoney(Model model, NotEnoughMoney ex) {
        model.addAttribute("notEnoughMoney", true);
        log.info("NotEnoughMoney" + ex.getMessage());
        return "not-enough-money";
    }

    @ExceptionHandler({EntityNotFound.class, UnreachableRequest.class})
    public String handlingNotFoundExcursion(Exception ex) {
        log.info(ex.getMessage());
        return "404";
    }

    @ExceptionHandler(OrderSaveException.class)
    public String orderSaveExceptionHandling(OrderSaveException ex) {
        log.info("OrderSaveException: " + ex.getMessage());
        return "unsuccess-buy";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handling() {
        return "404";
    }


}
