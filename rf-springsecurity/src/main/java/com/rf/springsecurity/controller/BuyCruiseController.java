package com.rf.springsecurity.controller;

import com.rf.springsecurity.domain.cruises.Ticket;
import com.rf.springsecurity.dto.OrderDTO;
import com.rf.springsecurity.exceptions.UnhandledCruiseName;
import com.rf.springsecurity.exceptions.UnhandledUserName;
import com.rf.springsecurity.services.BuyCruiseService;
import com.rf.springsecurity.services.CruiseService;
import com.rf.springsecurity.services.UserAuthenticationService;
import com.rf.springsecurity.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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
    public String buyCruise(@PathVariable("name") String name, @ModelAttribute("orderDTO") OrderDTO orderDTO){
        try {
            orderDTO.setCruise(cruiseService.getCruiseDataByName(name));
            orderDTO.setUser(userAuthenticationService.getAuthenticatedUser());
            buyCruiseService.buy(orderDTO);
        } catch (UnhandledCruiseName unhandledCruiseName) {
            //TODO Exception Handling
            unhandledCruiseName.printStackTrace();
        } catch (UnhandledUserName unhandledUserName) {
            unhandledUserName.printStackTrace();
        }
        return "redirect:/main";
    }
}
