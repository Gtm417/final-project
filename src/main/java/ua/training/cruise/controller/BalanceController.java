package ua.training.cruise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ua.training.cruise.controller.util.Util;
import ua.training.cruise.dto.BalanceDTO;
import ua.training.cruise.service.UserService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;


@Controller
public class BalanceController {

    private final UserService userService;

    @Autowired
    public BalanceController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/balance")
    public String balanceReplenish(Model model) {
        model.addAttribute("balanceDTO", new BalanceDTO());
        return "balance";
    }

    @PostMapping("/balance")
    public String replenishment(@Valid @ModelAttribute("balanceDTO") BalanceDTO balanceDTO, BindingResult bindingResult,
                                HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "balance";
        }
        Util.createUpdateUserInSession(
                userService.addBalance(
                        Util.getUserFromSession(session),
                        balanceDTO),
                session
        );

        return "redirect:/balance";
    }
}
