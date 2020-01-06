package com.rf.springsecurity.controller;

import com.rf.springsecurity.domain.cruises.Cruise;
import com.rf.springsecurity.domain.cruises.Ticket;
import com.rf.springsecurity.domain.ports.Excursion;
import com.rf.springsecurity.dto.ExcursionsDTO;
import com.rf.springsecurity.dto.OrderDTO;
import com.rf.springsecurity.exceptions.NotEnoughMoney;
import com.rf.springsecurity.exceptions.UnsupportedCruiseName;
import com.rf.springsecurity.services.BuyCruiseService;
import com.rf.springsecurity.services.CruiseService;
import com.rf.springsecurity.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

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
                                   OrderDTO orderDTO, Ticket ticket, ExcursionsDTO excursionsDTO,ExcursionsDTO excursionsDTOTest, Model model) throws UnsupportedCruiseName{

        Cruise cruise = cruiseService.getCruiseDataByName(name);
        System.out.println(cruise.getTickets());
        model.addAttribute("cruise", cruise);
        model.addAttribute("name", name);
        model.addAttribute("orderDTO", orderDTO);
        model.addAttribute("modelTicket", ticket);
        model.addAttribute("excursionDTO",new ExcursionsDTO(cruiseService.getAllExcursionsInCruise(cruise)));
        model.addAttribute("excursionsDTOTest", excursionsDTOTest);
        return "buy-cruise";
    }

    @PostMapping("/cruise/{name}/buy")
    public String buyCruise(@PathVariable("name") String name,
                            @ModelAttribute("orderDTO") OrderDTO orderDTO,
                            @ModelAttribute("modelTicket") Ticket ticket,
                            @ModelAttribute("excursionDTO") ExcursionsDTO excursionsDTO,
                            @ModelAttribute("excursionsDTOTest")ExcursionsDTO excursionsDTOTest
                            ) throws Exception {
        System.err.println(ticket);
        System.err.println(excursionsDTOTest);
        //System.err.println(excursionsDTO.toString());
            orderDTO.setCruise(cruiseService.getCruiseDataByName(name));
            orderDTO.setUser(userService.getAuthenticatedUser());
            orderDTO.setTicket(ticket);
            orderDTO.setExcursions(excursionsDTOTest.getExcursionsDTO());
        //System.out.println(orderDTO.toString());

       buyCruiseService.buy(orderDTO);
        return "redirect:/main";
    }
}
