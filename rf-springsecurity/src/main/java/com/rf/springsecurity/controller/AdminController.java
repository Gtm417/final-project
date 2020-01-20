package com.rf.springsecurity.controller;


import com.rf.springsecurity.dto.CruiseDescriptionsDTO;
import com.rf.springsecurity.dto.TicketDTO;
import com.rf.springsecurity.entity.cruise.Cruise;
import com.rf.springsecurity.services.CruiseService;
import com.rf.springsecurity.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static com.rf.springsecurity.controller.SessionAttributeConstants.SESSION_CRUISE;


@Controller
@RequestMapping("/cruise/edit")
public class AdminController {
    private final CruiseService cruiseService;
    private final OrderService orderService;

    @Autowired
    public AdminController(CruiseService cruiseService, OrderService orderService) {
        this.cruiseService = cruiseService;
        this.orderService = orderService;
    }

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public String getCruiseEditPage(){
        return "cruise-edit";
    }

    @GetMapping("/description")
    @PreAuthorize("hasRole('ADMIN')")
    public String getDescriptionEditPage(Model model){
        model.addAttribute("descriptionDTO", new CruiseDescriptionsDTO());
        return "cruise/edit-description";
    }

    @PostMapping("/descriptionEdit")
    @PreAuthorize("hasRole('ADMIN')")
    public String editCruiseDescription(@ModelAttribute("descriptionDTO") CruiseDescriptionsDTO cruiseDescriptionsDTO, HttpSession session){
        cruiseService.changeCruiseDescription((Cruise) session.getAttribute(SESSION_CRUISE), cruiseDescriptionsDTO);
        return "cruise/edit-description";
    }

    @GetMapping("/add/ticket")
    @PreAuthorize("hasRole('ADMIN')")
    public String getAddTicketPage(TicketDTO ticketDTO, Model model){
        model.addAttribute("ticketDTO", ticketDTO);
        return "cruise/add-ticket";
    }

    @PostMapping("/adding-ticket")
    @PreAuthorize("hasRole('ADMIN')")
    public String getAddTicketPage(@Valid @ModelAttribute TicketDTO ticketDTO,
                                   BindingResult bindingResult, HttpSession session){
        if(bindingResult.hasErrors()){
            return "cruise/add-ticket";
        }
        cruiseService.addNewTicketToCruise(ticketDTO, (Cruise)session.getAttribute(SESSION_CRUISE));
        return "cruise/add-ticket";
    }

    @GetMapping("/all_passengers")
    @PreAuthorize("hasRole('ADMIN')")
    public String getAllPassengers( Model model,HttpSession session){
        model.addAttribute("passengers", orderService.findAllOrdersByCruise((Cruise)session.getAttribute(SESSION_CRUISE)));
        return "cruise/cruise-passengers";
    }



}
