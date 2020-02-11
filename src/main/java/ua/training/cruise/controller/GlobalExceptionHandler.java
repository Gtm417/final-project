package ua.training.cruise.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import ua.training.cruise.exception.*;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UnsupportedCruise.class)
    public String handlingUnsupportedCruiseName(UnsupportedCruise ex) {
        log.error("Cruise not found: " + ex.getMessage());
        return "404";
    }

    @ExceptionHandler(NotEnoughMoney.class)
    public String handlingNotEnoughMoney(Model model, NotEnoughMoney ex) {
        model.addAttribute("notEnoughMoney", true);
        log.info(ex.getMessage());
        return "not-enough-money";
    }

    @ExceptionHandler({NotFoundExcursion.class, UnreachableRequest.class})
    public String handlingNotFoundExcursion(Exception ex) {
        log.info(ex.getMessage());
        return "404";
    }

    @ExceptionHandler(NoPlaceOnShip.class)
    public String handlingNoPlacesOnShip(NoPlaceOnShip ex, Model model) {
        log.info(ex.getMessage());
        model.addAttribute("id", ex.getId());
        model.addAttribute("noPlace", true);
        return "redirect:/user/cruise";
    }


    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handling() {
        return "404";
    }


}
