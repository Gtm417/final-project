package com.rf.springsecurity.controller;

import com.rf.springsecurity.entity.cruises.Cruise;
import com.rf.springsecurity.entity.cruises.Ticket;
import com.rf.springsecurity.dto.OrderDTO;
import com.rf.springsecurity.entity.ports.Excursion;
import com.rf.springsecurity.exceptions.NotEnoughMoney;
import com.rf.springsecurity.exceptions.UnsupportedCruiseName;
import com.rf.springsecurity.exceptions.UnsupportedTicketId;
import com.rf.springsecurity.repository.ExcursionRepository;
import com.rf.springsecurity.services.BuyCruiseService;
import com.rf.springsecurity.services.CruiseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/cruise")
public class BuyCruiseController {



    private BuyCruiseService buyCruiseService;
    private CruiseService cruiseService;
    //TODO to cruiseService
    private final ExcursionRepository excursionRepository;

    @Autowired
    public BuyCruiseController(BuyCruiseService buyCruiseService, CruiseService cruiseService, ExcursionRepository excursionRepository) {
        this.buyCruiseService = buyCruiseService;
        this.cruiseService = cruiseService;
        this.excursionRepository = excursionRepository;
    }

    //Передавать  сразу круиз кнопки посмотреть круиз (в теле запроса)
    @GetMapping("/{name}/buy")
    public String getCruiseBuyForm(@PathVariable("name") String name,
                                  OrderDTO orderDTO, Model model) throws UnsupportedCruiseName{
        System.out.println("open -> /{name}/buy");
        Cruise cruise = cruiseService.getCruiseDataByName(name);
        model.addAttribute("cruise", cruise);
        System.out.println("2");
        System.out.println(excursionRepository.findAllByCruiseID(cruise.getId()).stream().map(Excursion::getExcursionName).collect(Collectors.joining(",")));
//        List<List<Excursion>> excursions = cruise.getPorts().stream()
//               .map((Port::getExcursions))
//               //.flatMap(Collection::stream)
//               .collect(Collectors.toList());
//ZZ
//        System.out.println(cruise.getPorts().stream()
//               .map((Port::getExcursions))
//                //.flatMap(Collection::stream)
//              .collect(Collectors.toList()));

        //model.addAttribute("allExcursions", new ExcursionsDTO(cruiseService.getAllExcursionsInCruise(cruise)));
        System.out.println("2");
        model.addAttribute("name", name);
        model.addAttribute("orderDTO", orderDTO);
        System.out.println("3");
        //TODO ticket give new request to DB
        model.addAttribute("ticketsPrice",cruise.getTickets().stream()
               .mapToLong(ticket -> buyCruiseService.getTicketPriceWithDiscount(ticket)).toArray());
        System.out.println("3");
        System.out.println("4");
        buyCruiseService.getTicketPriceWithDiscount(new Ticket());
                //cruise.getTickets().forEach(ticket -> buyCruiseService.getTicketPriceWithDiscount(ticket)));

        System.out.println("4");
        System.out.println("close -> /{name}/buy");

        return "buy-cruise";
    }


    //TODO handling Not Enough Money Exc
    //TODO handling UnsupportedTicketId
    @PostMapping("/buy")
    public String buyCruise(@Valid @ModelAttribute("orderDTO") OrderDTO orderDTO,@ModelAttribute("cruise") Cruise cruise,
                            BindingResult bindingResult) throws NotEnoughMoney, UnsupportedCruiseName, UnsupportedTicketId {
        if(bindingResult.hasErrors()){
            return "buy-cruise";
        }
        buyCruiseService.buy(orderDTO);
        return "redirect:/main";
    }
}
