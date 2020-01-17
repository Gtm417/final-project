package com.rf.springsecurity.controller;

import com.rf.springsecurity.dto.ExcursionsDTO;
import com.rf.springsecurity.dto.TestOrderDTO;
import com.rf.springsecurity.entity.cruise.Cruise;
import com.rf.springsecurity.entity.port.Excursion;
import com.rf.springsecurity.entity.port.Port;
import com.rf.springsecurity.exceptions.UnsupportedCruiseName;
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
import javax.validation.Valid;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.joining;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final CruiseService cruiseService;
    @Autowired
    private final Util util;

    public UserController(UserService userService, CruiseService cruiseService, Util util) {
        this.userService = userService;
        this.cruiseService = cruiseService;
        this.util = util;
    }

    @GetMapping
    public String main(HttpSession session) {
        session.setAttribute("user", userService.getAuthenticatedUser());
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
        session.setAttribute("cruise", cruise);
        model.addAttribute("cruise", cruise);
        model.addAttribute("ports", cruise.getPorts().stream().map(Port::getPortName).collect(joining(",")));
        return "cruise";
    }


    @GetMapping("/cruise/buy")
    public String getCruiseBuyForm( Model model, HttpSession session) throws UnsupportedCruiseName{
        Cruise cruise = (Cruise) session.getAttribute("cruise");
        model.addAttribute("cruise", cruise);
        List<Excursion> excursionsDTO = cruiseService.getAllExcursionsByCruiseId(cruise.getId());
        //System.err.println(excursionsDTO.toString());
        util.addListOfExcursionsToSession(session);
        model.addAttribute("excursions", excursionsDTO);
        model.addAttribute("excursionDTO", new Excursion());
        model.addAttribute("orderDTO", new TestOrderDTO());
        model.addAttribute("ticketsPrice",cruiseService.listOfTicketPriceWithDiscount(cruise));
        return "buy-cruise";
    }

    //TODO обнулить сессию екскурсий
    @PostMapping("/cruise/buy")
    public String buyCruise(@ModelAttribute("orderDTO") TestOrderDTO orderDTO, Model model, HttpSession session){
        //System.err.println(orderDTO.getTicket());
        System.out.println("BEFORE" + ((ExcursionsDTO) session.getAttribute("excursionsDTO")).getExcursionsDTO());
        orderDTO.setExcursions(((ExcursionsDTO) session.getAttribute("excursionsDTO")).getExcursionsDTO());


        System.out.println(orderDTO.toString());
        // FIRST SEND DATA AFTER RESET
        util.resetExcursionSession(session);
        System.out.println("AFTER" + ((ExcursionsDTO) session.getAttribute("excursionsDTO")).getExcursionsDTO());
        return "redirect:/user/cruise/buy";
    }

    @PostMapping(value = "/add/excursion")
    @ResponseBody
    public ResponseEntity<Excursion> addExcursion (HttpSession session, @ModelAttribute("excursionDTO") Excursion excursion) throws Exception {
        System.out.println(excursion.toString());
        util.addExcursionToListInSession(session, excursion);
        System.err.println(session.getAttribute("excursionsDTO"));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



}
