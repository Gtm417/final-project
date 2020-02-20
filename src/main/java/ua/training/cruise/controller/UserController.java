package ua.training.cruise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.training.cruise.controller.util.Util;
import ua.training.cruise.dto.OrderDTO;
import ua.training.cruise.entity.cruise.Cruise;
import ua.training.cruise.entity.port.Port;
import ua.training.cruise.service.CruiseService;
import ua.training.cruise.service.OrderService;
import ua.training.cruise.service.TicketService;
import ua.training.cruise.service.UserService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static java.util.stream.Collectors.joining;
import static ua.training.cruise.controller.SessionAttributeConstants.*;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final CruiseService cruiseService;
    private final OrderService orderService;
    private final TicketService ticketService;


    @Autowired
    public UserController(UserService userService, CruiseService cruiseService,
                          OrderService orderService, TicketService ticketService) {
        this.userService = userService;
        this.cruiseService = cruiseService;
        this.orderService = orderService;
        this.ticketService = ticketService;
    }

    @GetMapping("/error")
    public String getErrorPage() {
        return "error-page";
    }

    @GetMapping
    public String getUserPage(HttpSession session, @AuthenticationPrincipal User user) {
        session.setAttribute(SESSION_USER, userService.getUserByLogin(user.getUsername()));
        return "user-page";
    }

    @GetMapping("/main")
    public String getMainPage(Model model, HttpSession session) {
        Util.clearBuySessionAttributes(session);
        model.addAttribute("cruises", cruiseService.getAllCruises());
        return "main";
    }

    @RequestMapping("/cruise")
    public String getCruisePage(@RequestParam("id") Long id,
                                @RequestParam(value = "noPlace", required = false) Boolean noPlace,
                                Model model,
                                HttpSession session) {
        Cruise cruise = cruiseService.findCruiseById(id);
        session.setAttribute(SESSION_CRUISE, cruise);
        model.addAttribute("noPlace", noPlace);
        model.addAttribute("cruise", cruise);
        model.addAttribute("ports", cruise.getPorts().stream().map(Port::getPortName).collect(joining(",")));
        return "cruise";
    }


    @GetMapping("/cruise/order")
    public String getCruiserOrderForm(Model model, HttpSession session) {
        model.addAttribute("tickets", ticketService.showAllTicketsForCruise(Util.getSessionCruise(session)));
        model.addAttribute("order", new OrderDTO());

        return "buy-cruise";
    }

    @PostMapping("/cruise/order")
    public String buyCruise(@Valid @ModelAttribute("order") OrderDTO orderDTO, BindingResult bindingResult,
                            RedirectAttributes redirectAttributes, HttpSession session) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("fieldErrors", bindingResult.getFieldErrors());
            return "redirect:/user/cruise/order";
        }
        session.setAttribute(SESSION_ORDER, orderService.getEntityFromDTO(orderDTO));
        return "redirect:/user/cruise/buy";
    }


    @PostMapping("/cruise/buy")
    public String submitBuy(HttpSession session) {
        orderService.buyCruise(Util.getSessionOrder(session),
                Util.getSessionCruise(session),
                Util.getUserFromSession(session));

        return "redirect:/user/buy/success";
    }

    @GetMapping("buy/success")
    public String successBuy(HttpSession session) {
        Util.clearBuySessionAttributes(session);
        return "success-buy";
    }


    @GetMapping(value = "/orders")
    public String getAllOrders(HttpSession session, Model model,
                               @PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC, size = 4) Pageable pageable) {
        model.addAttribute("page", orderService.findAllOrdersByUser(Util.getUserFromSession(session), pageable));
        return "orders";
    }


}
