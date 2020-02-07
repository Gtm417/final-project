package com.rf.springsecurity.controller;

import com.rf.springsecurity.entity.user.User;
import com.rf.springsecurity.services.UserService;
import com.rf.springsecurity.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static com.rf.springsecurity.controller.SessionAttributeConstants.SESSION_USER;


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
                                HttpSession session){
        util.addUserToSession(
                userService.addBalance(
                    (User) session.getAttribute(SESSION_USER),
                    balance
                    ),
                session
        );

        return "redirect:/balance";
    }
}
