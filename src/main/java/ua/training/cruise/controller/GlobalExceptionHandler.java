package ua.training.cruise.controller;


import lombok.extern.log4j.Log4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import ua.training.cruise.exception.EntityNotFound;
import ua.training.cruise.exception.OrderSaveException;
import ua.training.cruise.exception.UnreachableRequest;
import ua.training.cruise.exception.UnsupportedCruise;

@Log4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UnsupportedCruise.class)
    public String handlingUnsupportedCruiseName(UnsupportedCruise ex) {
        log.info("Cruise not found: " + ex.getMessage());
        return "404";
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
