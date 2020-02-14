package ua.training.cruise.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.training.cruise.dto.RegistrationDTO;
import ua.training.cruise.exception.DataBaseDuplicateConstraint;
import ua.training.cruise.service.UserService;

import javax.validation.Valid;

@Slf4j
@Controller
public class RegistrationController {

    private UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public ModelAndView registration(@RequestParam(value = "error", required = false) String error) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("error", error != null);
        modelAndView.addObject("registrationDTO", new RegistrationDTO());
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @PostMapping("/registration")
    public String addUser(@Valid @ModelAttribute RegistrationDTO registrationDTO,
                          BindingResult bindingResult) throws DataBaseDuplicateConstraint {
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        userService.saveNewUser(registrationDTO);
        return "redirect:/login";
    }

    @ExceptionHandler(DataBaseDuplicateConstraint.class)
    public String duplicateConstraintHandling(DataBaseDuplicateConstraint ex) {
        log.info("registration error: " + ex.getMessage());
        return "redirect:/registration?error";
    }
}


