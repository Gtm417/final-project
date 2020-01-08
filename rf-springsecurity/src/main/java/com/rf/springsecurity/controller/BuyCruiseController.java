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

@Controller
@RequestMapping("/cruise")
public class BuyCruiseController {

    //TODO handling Not Enough Money Exc

    private BuyCruiseService buyCruiseService;
    private CruiseService cruiseService;

    @Autowired
    public BuyCruiseController(BuyCruiseService buyCruiseService, CruiseService cruiseService) {
        this.buyCruiseService = buyCruiseService;
        this.cruiseService = cruiseService;
    }

    @GetMapping("/{name}/buy")
    public String getCruiseBuyForm(@PathVariable("name") String name,
                                   OrderDTO orderDTO, Model model) throws UnsupportedCruiseName{
        Cruise cruise = cruiseService.getCruiseDataByName(name);
        model.addAttribute("cruise", cruise);
        model.addAttribute("allExcursions", new ExcursionsDTO(cruiseService.getAllExcursionsInCruise(cruise)));
        model.addAttribute("name", name);
        model.addAttribute("orderDTO", orderDTO);
        model.addAttribute("ticketsPrice",cruise.getTickets().stream()
                .mapToLong(ticket -> buyCruiseService.getTicketPriceWithDiscount(ticket)).toArray());

        return "buy-cruise";
    }

    @PostMapping("/buy")
    public String buyCruise(@ModelAttribute("orderDTO") OrderDTO orderDTO) throws Exception {
        System.err.println(orderDTO.getTicket_id());
        System.err.println(orderDTO.getExcursionNames());
        System.err.println(orderDTO.getCruiseName());
        buyCruiseService.buy(orderDTO);
        return "redirect:/main";
    }
}
