package ua.training.cruise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ua.training.cruise.controller.util.Util;
import ua.training.cruise.service.UserService;

import javax.servlet.http.HttpSession;


@Controller
public class BalanceController {

    private final UserService userService;
    private final Util util;

    @Autowired
    public BalanceController(UserService userService, Util util) {
        this.userService = userService;
        this.util = util;
    }

    @GetMapping("/balance")
    public String balanceReplenish(Model model) {
        return "balance";
    }

    @PostMapping("/replenishment")
    public String replenishment(@ModelAttribute("balance") Long balance,
                                HttpSession session) {
        Util.createUpdateUserToSession(
                userService.addBalance(
                        Util.getUserFromSession(session),
                        balance),
                session
        );

        return "redirect:/balance";
    }
}
