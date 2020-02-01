package com.rf.springsecurity.controller;

import com.rf.springsecurity.dto.ExcursionsDTO;
import com.rf.springsecurity.dto.OrderDTO;
import com.rf.springsecurity.dto.OrdersDTO;
import com.rf.springsecurity.entity.cruise.Cruise;
import com.rf.springsecurity.entity.port.Excursion;
import com.rf.springsecurity.entity.port.Port;
import com.rf.springsecurity.entity.user.User;
import com.rf.springsecurity.exceptions.NotEnoughMoney;
import com.rf.springsecurity.exceptions.UnsupportedCruiseName;
import com.rf.springsecurity.services.BuyCruiseService;
import com.rf.springsecurity.services.CruiseService;
import com.rf.springsecurity.services.OrderService;
import com.rf.springsecurity.services.UserService;
import com.rf.springsecurity.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


import java.util.Set;

import static com.rf.springsecurity.controller.SessionAttributeConstants.*;
import static java.util.stream.Collectors.joining;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final CruiseService cruiseService;
    private final BuyCruiseService buyCruiseService;
    private final OrderService orderService;

    private final Util util;


    @Autowired
    public UserController(UserService userService, CruiseService cruiseService, Util util,
                          BuyCruiseService buyCruiseService, OrderService orderService) {
        this.userService = userService;
        this.cruiseService = cruiseService;
        this.util = util;
        this.buyCruiseService = buyCruiseService;
        this.orderService = orderService;
    }

    @GetMapping("/error")
    public String getErrorPage() {
        return "error-page";
    }

    @GetMapping
    public String main(HttpSession session) {
        session.setAttribute(SESSION_USER, userService.getAuthenticatedUser());
        return "user-page";
    }

    @GetMapping("/main")
    public String getMainPage(@RequestParam(value = "error", required = false) String error,
                              Model model) {
        model.addAttribute("error", error);
        model.addAttribute("cruises", cruiseService.getAllCruises());
        return "main";
    }

    @GetMapping("/cruise")
    public String getCruisePage(@RequestParam("id") Long id,
                                Model model,
                                HttpSession session) throws UnsupportedCruiseName {
        Cruise cruise = cruiseService.findCruiseById(id);
        session.setAttribute(SESSION_CRUISE, cruise);
        model.addAttribute("cruise", cruise);
        model.addAttribute("ports", cruise.getPorts().stream().map(Port::getPortName).collect(joining(",")));
        return "cruise";
    }


    @GetMapping("/cruise/buy")
    public String getCruiseBuyForm(Model model,
                                   HttpSession session) {
        Cruise cruise = (Cruise) session.getAttribute(SESSION_CRUISE);
        //util.addSetOfExcursionsToSession(session);
        //model.addAttribute("cruise", cruise);
        //model.addAttribute("excursions", cruiseService.getAllExcursionsByCruiseId(cruise.getId()));
        //model.addAttribute("excursionDTO", new Excursion());
        model.addAttribute("orderDTO", new OrderDTO());
        model.addAttribute("ticketsPrice", cruiseService.listOfTicketPriceWithDiscount(cruise));
        return "buy-cruise";
    }

    //TODO обнулить сессию екскурсий
    @PostMapping("/cruise/buy")
    public String buyCruise(@ModelAttribute("orderDTO") OrderDTO orderDTO,
                            HttpSession session) throws NotEnoughMoney {
        //orderDTO.setExcursions(((ExcursionsDTO) session.getAttribute(SESSION_EXCURSIONS)).getExcursionsDTO());
        //orderDTO.setOrderPrice(cruiseService.getTicketPriceWithDiscount(orderDTO.getTicket()));
        session.setAttribute(SESSION_ORDER, orderDTO);
        System.out.println(orderDTO);
        session.removeAttribute(SESSION_EXCURSIONS);
//        buyCruiseService.buyCruise(orderDTO,
//                (Cruise) session.getAttribute(SESSION_CRUISE),
//                (User) session.getAttribute(SESSION_USER)
//        );

        //util.resetExcursionSession(session);
       // return "redirect:/user/cruise/buy";
        return "redirect:/user/cruise/buy-submit";
    }

    @GetMapping("/cruise/buy-submit")
    public String submitBuyPage(Model model, HttpSession session) {
        Cruise cruise = (Cruise) session.getAttribute(SESSION_CRUISE);
        //model.addAttribute("cruise", cruise);
        util.addSetOfExcursionsToSession(session);
        model.addAttribute("excursions", cruiseService.getAllExcursionsByCruiseId(cruise.getId()));
        OrderDTO orderDTO = (OrderDTO) session.getAttribute("order"); // thymeleaf get session.order
        //todo add to table ticket value price with discount, calc 1 time before insert new Ticket
        orderDTO.setOrderPrice(cruiseService.getTicketPriceWithDiscount(orderDTO.getTicket()));
        model.addAttribute("resultPrice",
                ((OrderDTO) session.getAttribute("order")).getOrderPrice() + util.getTotalPriceSelectedExcursions(session));// перенести в сервис ++
        return "submit-form";
    }

    @PostMapping("/cruise/buy-submit")
    public String submitBuy(@ModelAttribute("resultPrice") Long resultPrice,
                            HttpSession session) throws NotEnoughMoney {
            OrderDTO orderDTO = (OrderDTO) session.getAttribute(SESSION_ORDER);
            orderDTO.setOrderPrice(resultPrice);
            System.out.println(resultPrice);
            buyCruiseService.buyCruise(orderDTO,
                    (Cruise)session.getAttribute(SESSION_CRUISE),
                    (User)session.getAttribute(SESSION_USER));
        return "redirect:/user/success-buy";
    }

    @GetMapping("success-buy")
    public String successBuy(HttpSession session){
        session.removeAttribute(SESSION_ORDER);
        session.removeAttribute(SESSION_CRUISE);
        session.removeAttribute(SESSION_EXCURSIONS);
        return "success-buy";
    }


    @PostMapping(value = "/add/excursion")
    public String addExcursion(@ModelAttribute("excursionDTO") Excursion excursion,
                                                  @ModelAttribute("portId") Long portId,
                                                  @ModelAttribute("portName") String portName,
                                                  HttpSession session) {
        excursion.setPort(Port.builder().id(portId).portName(portName).build());
        System.out.println(excursion);
        util.addExcursionToListInSession(session, excursion);
        return "redirect:/user/cruise/buy-submit";
    }

    @PostMapping(value = "/remove/excursion")
    public String  removeExcursion(@ModelAttribute("excursionDTO") Excursion excursion,
                                                     HttpSession session) {
        util.removeExcursionFromSession(session, excursion);
        return  "redirect:/user/cruise/buy-submit";
    }

    @GetMapping(value = "/orders")
    public String getAllOrders(HttpSession session, Model model) {
        model.addAttribute("orders", orderService.findAllOrdersByUser((User) session.getAttribute(SESSION_USER)));
        return "orders";
    }


}
