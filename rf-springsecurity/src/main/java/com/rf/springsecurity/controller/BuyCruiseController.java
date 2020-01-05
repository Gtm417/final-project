package com.rf.springsecurity.controller;

import com.rf.springsecurity.dto.OrderDTO;
import com.rf.springsecurity.exceptions.UnsupportedCruiseName;
import com.rf.springsecurity.exceptions.UnsupportedUserName;
import com.rf.springsecurity.services.BuyCruiseService;
import com.rf.springsecurity.services.CruiseService;
import com.rf.springsecurity.services.UserAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class BuyCruiseController {

    private BuyCruiseService buyCruiseService;
    private CruiseService cruiseService;
    private final UserAuthenticationService userAuthenticationService;


    @Autowired
    public BuyCruiseController(BuyCruiseService buyCruiseService, CruiseService cruiseService,UserAuthenticationService userAuthenticationService) {
        this.buyCruiseService = buyCruiseService;
        this.cruiseService = cruiseService;
        this.userAuthenticationService = userAuthenticationService;
    }

    @GetMapping("/cruise/{name}/buy")
    public String getCruiseBuyForm(@PathVariable("name") String name, OrderDTO orderDTO, Model model){
        model.addAttribute("name", name);
        model.addAttribute("orderDTO", orderDTO);
        return "buy-cruise";
    }

    @PostMapping("/cruise/{name}/buy")
    public String buyCruise(@PathVariable("name") String name, @ModelAttribute("orderDTO") OrderDTO orderDTO) throws UnsupportedCruiseName,UnsupportedUserName{
        try {
            orderDTO.setCruise(cruiseService.getCruiseDataByName(name));
            // TODO orderDTO.setUser(userAuthenticationService.getAuthenticatedUser());
            buyCruiseService.buy(orderDTO);
        } catch (UnsupportedCruiseName unsupportedCruiseName) {
            //TODO Exception Handling
            unsupportedCruiseName.printStackTrace();
        }
        return "redirect:/main";
    }
}
