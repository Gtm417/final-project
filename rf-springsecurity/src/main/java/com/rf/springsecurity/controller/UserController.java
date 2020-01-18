package com.rf.springsecurity.controller;

import com.rf.springsecurity.dto.ExcursionsDTO;
import com.rf.springsecurity.dto.OrderDTO;
import com.rf.springsecurity.entity.cruise.Cruise;
import com.rf.springsecurity.entity.port.Excursion;
import com.rf.springsecurity.entity.port.Port;
import com.rf.springsecurity.entity.user.User;
import com.rf.springsecurity.exceptions.NotEnoughMoney;
import com.rf.springsecurity.exceptions.UnsupportedCruiseName;
import com.rf.springsecurity.services.BuyCruiseService;
import com.rf.springsecurity.services.CruiseService;
import com.rf.springsecurity.services.UserService;
import com.rf.springsecurity.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;


import static com.rf.springsecurity.controller.SessionAttributeConstants.*;
import static java.util.stream.Collectors.joining;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final CruiseService cruiseService;
    private final BuyCruiseService buyCruiseService;

    private final Util util;


    @Autowired
    public UserController(UserService userService, CruiseService cruiseService, Util util, BuyCruiseService buyCruiseService) {
        this.userService = userService;
        this.cruiseService = cruiseService;
        this.util = util;
        this.buyCruiseService = buyCruiseService;
    }

    @GetMapping
    public String main(HttpSession session) {
        session.setAttribute(SESSION_USER, userService.getAuthenticatedUser());
        return "user-page";
    }

    @GetMapping("/main")
    public String getMainPage(@RequestParam(value = "error", required = false) String error,
                              Model model) {
        model.addAttribute("error", error );
        model.addAttribute("cruises", cruiseService.getAllCruises());
        return "main";
    }

    @GetMapping("/cruise")
    public String  getCruisePage(@RequestParam("id") Long id, Model model,HttpSession session) throws UnsupportedCruiseName {
        Cruise cruise = cruiseService.findCruiseById(id);
        session.setAttribute(SESSION_CRUISE, cruise);
        model.addAttribute("cruise", cruise);
        model.addAttribute("ports", cruise.getPorts().stream().map(Port::getPortName).collect(joining(",")));
        return "cruise";
    }


    @GetMapping("/cruise/buy")
    public String getCruiseBuyForm( Model model, HttpSession session){
        Cruise cruise = (Cruise) session.getAttribute(SESSION_CRUISE);
        util.addListOfExcursionsToSession(session);
        model.addAttribute("cruise", cruise);
        model.addAttribute("excursions", cruiseService.getAllExcursionsByCruiseId(cruise.getId()));
        model.addAttribute("excursionDTO", new Excursion());
        model.addAttribute("orderDTO", new OrderDTO());
        model.addAttribute("ticketsPrice",cruiseService.listOfTicketPriceWithDiscount(cruise));
        return "buy-cruise";
    }

    //TODO обнулить сессию екскурсий
    @PostMapping("/cruise/buy")
    public String buyCruise(@ModelAttribute("orderDTO") OrderDTO orderDTO, HttpSession session) throws NotEnoughMoney{
        orderDTO.setExcursions(((ExcursionsDTO) session.getAttribute(SESSION_EXCURSIONS)).getExcursionsDTO());
        buyCruiseService.buyCruise(orderDTO,
                (Cruise) session.getAttribute(SESSION_CRUISE),
                (User)session.getAttribute(SESSION_USER)
        );

        util.resetExcursionSession(session);
        return "redirect:/user/cruise/buy";
    }

    @PostMapping(value = "/add/excursion")
    @ResponseBody
    public ResponseEntity<Excursion> addExcursion (HttpSession session,
                                                   @ModelAttribute("excursionDTO") Excursion excursion) throws Exception {
        util.addExcursionToListInSession(session, excursion);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



}
