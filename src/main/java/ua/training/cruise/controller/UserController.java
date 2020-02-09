package ua.training.cruise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.training.cruise.controller.util.Util;
import ua.training.cruise.entity.cruise.Cruise;
import ua.training.cruise.entity.order.Order;
import ua.training.cruise.entity.port.Port;
import ua.training.cruise.exception.NoPlaceOnShip;
import ua.training.cruise.exception.NotEnoughMoney;
import ua.training.cruise.exception.UnsupportedCruise;
import ua.training.cruise.service.CruiseService;
import ua.training.cruise.service.OrderService;
import ua.training.cruise.service.UserService;

import javax.servlet.http.HttpSession;
import java.util.HashSet;

import static java.util.stream.Collectors.joining;
import static ua.training.cruise.controller.SessionAttributeConstants.*;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final CruiseService cruiseService;
    private final OrderService orderService;
    Util util;


    @Autowired
    public UserController(UserService userService, CruiseService cruiseService,
                          OrderService orderService) {
        this.userService = userService;
        this.cruiseService = cruiseService;
        this.orderService = orderService;
    }

    @GetMapping("/error")
    public String getErrorPage() {
        return "error-page";
    }

    @GetMapping
    public String user(HttpSession session) {
        session.setAttribute(SESSION_USER, userService.getAuthenticatedUser());
        return "user-page";
    }

    @GetMapping("/main")
    public String getMainPage(@RequestParam(value = "error", required = false) String error,
                              Model model, HttpSession session) {
        Util.clearBuySessionAttributes(session);
        model.addAttribute("error", error);
        model.addAttribute("cruises", cruiseService.getAllCruises());
        return "main";
    }

    @GetMapping("/cruise")
    public String getCruisePage(@RequestParam("id") Long id,
                                Model model,
                                HttpSession session) throws UnsupportedCruise {
        Cruise cruise = cruiseService.findCruiseById(id);
        session.setAttribute(SESSION_CRUISE, cruise);
        model.addAttribute("cruise", cruise);
        model.addAttribute("ports", cruise.getPorts().stream().map(Port::getPortName).collect(joining(",")));
        return "cruise";
    }


    @GetMapping("/cruise/buy")
    public String getCruiseBuyForm(Model model, HttpSession session) {

        model.addAttribute("tickets", cruiseService.showAllTicketsForCruise(Util.getSessionCruise(session)));
        model.addAttribute("order", new Order());
        return "buy-cruise";
    }

    @PostMapping("/cruise/buy")
    public String buyCruise(@ModelAttribute("order") Order order,
                            HttpSession session) {
        order.setExcursions(new HashSet<>());
        order.setOrderPrice(order.getTicket().getPriceWithDiscount());
        session.setAttribute(SESSION_ORDER, order);
        return "redirect:/user/cruise/buy-submit";
    }

    @GetMapping("/cruise/buy-submit")
    public String submitBuyPage(Model model, HttpSession session) {
        model.addAttribute("excursions", cruiseService.getAllExcursionsByCruiseId(Util.getSessionCruise(session).getId()));
        Order order = Util.getSessionOrder(session);
        order.setOrderPrice(orderService.getOrderTotalPrice(order));
        model.addAttribute("resultPrice", order.getOrderPrice());
        return "submit-form";
    }


    @PostMapping("/cruise/buy-submit")
    public String submitBuy(HttpSession session) throws NotEnoughMoney, NoPlaceOnShip {
        Order order = (Order) session.getAttribute(SESSION_ORDER);
        orderService.buyCruise(order,
                Util.getSessionCruise(session),
                Util.getUserFromSession(session));
        return "redirect:/user/success-buy";
    }

    @GetMapping("success-buy")
    public String successBuy(HttpSession session) {
        Util.clearBuySessionAttributes(session);
        return "success-buy";
    }


    @GetMapping(value = "/orders")
    public String getAllOrders(HttpSession session, Model model,
                               @PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC, size = 4) Pageable pageable) {
        //@RequestParam Integer size, @RequestParam Integer page
        //, @PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable
        //model.addAttribute("orders", orderService.findAllOrdersByUser(Util.getUserFromSession(session), pageable));
        model.addAttribute("page", orderService.findAllOrdersByUser(Util.getUserFromSession(session), pageable));
        return "orders";
    }


}
