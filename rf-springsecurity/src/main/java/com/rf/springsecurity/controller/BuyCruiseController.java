package com.rf.springsecurity.controller;

import com.rf.springsecurity.domain.cruises.Ticket;
import com.rf.springsecurity.dto.BalanceUserDTO;
import com.rf.springsecurity.dto.OrderDTO;
import com.rf.springsecurity.exceptions.UnhandledCruiseName;
import com.rf.springsecurity.services.BuyCruiseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RestController
public class BuyCruiseController {


    private BuyCruiseService buyCruiseService;

    @Autowired
    public BuyCruiseController(BuyCruiseService buyCruiseService) {
        this.buyCruiseService = buyCruiseService;
    }

    @GetMapping("/cruise/{name}/buy")
    public String getCruiseBuyForm(@PathVariable("name") String name){
        return "buy-cruise";
    }

    @PostMapping("/cruise/{name}/buy")
    public void buyCruise(@PathVariable("name") String name, @Valid @ModelAttribute("orderDTO") OrderDTO orderDTO){
        try {
            buyCruiseService.buy(orderDTO);
        } catch (UnhandledCruiseName unhandledCruiseName) {
            unhandledCruiseName.printStackTrace();
        }

    }


    @PostMapping("/test")
    public void test(){
        OrderDTO orderDTO = OrderDTO.builder()
                .cruiseName("name1")
                .firstName("tima")
                .secondName("tima")
                .ticket(Ticket.VIP)
                .userLogin("t")
                .build();
        try {
            buyCruiseService.buy(orderDTO);
        } catch (UnhandledCruiseName unhandledCruiseName) {
            unhandledCruiseName.printStackTrace();
        }
    }
}
