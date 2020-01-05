package com.rf.springsecurity.controller;

import com.rf.springsecurity.dto.OrderDTO;
import com.rf.springsecurity.exceptions.UnsupportedCruiseName;
import com.rf.springsecurity.exceptions.UnsupportedUserName;
import com.rf.springsecurity.services.BuyCruiseService;
import com.rf.springsecurity.services.CruiseService;
import com.rf.springsecurity.services.UserAuthenticationService;
import com.rf.springsecurity.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class BuyCruiseController {

    private BuyCruiseService buyCruiseService;
    private CruiseService cruiseService;
    private final UserService userService;


    @Autowired
    public BuyCruiseController(BuyCruiseService buyCruiseService, CruiseService cruiseService, UserService userService) {
        this.buyCruiseService = buyCruiseService;
        this.cruiseService = cruiseService;
        this.userService = userService;
    }

    @GetMapping("/cruise/{name}/buy")
    public String getCruiseBuyForm(@PathVariable("name") String name,
                                   OrderDTO orderDTO, Model model) throws UnsupportedCruiseName{
        cruiseService.getCruiseDataByName(name);
        model.addAttribute("name", name);
        model.addAttribute("orderDTO", orderDTO);
        return "buy-cruise";
    }

    @PostMapping("/cruise/{name}/buy")
    public String buyCruise(@PathVariable("name") String name,
                            @ModelAttribute("orderDTO") OrderDTO orderDTO) throws UnsupportedCruiseName {
            orderDTO.setCruise(cruiseService.getCruiseDataByName(name));
            orderDTO.setUser(userService.getAuthenticatedUser());
            buyCruiseService.buy(orderDTO);
        return "redirect:/main";
    }
}
