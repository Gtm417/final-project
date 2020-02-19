package ua.training.cruise.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.training.cruise.controller.util.Util;
import ua.training.cruise.dto.CruiseDescriptionsDTO;
import ua.training.cruise.dto.TicketDTO;
import ua.training.cruise.exception.DataBaseDuplicateConstraint;
import ua.training.cruise.service.CruiseService;
import ua.training.cruise.service.OrderService;
import ua.training.cruise.service.TicketService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/cruise/edit")
public class AdminController {
    private final CruiseService cruiseService;
    private final OrderService orderService;
    private final TicketService ticketService;

    @Autowired
    public AdminController(CruiseService cruiseService, OrderService orderService, TicketService ticketService) {
        this.cruiseService = cruiseService;
        this.orderService = orderService;
        this.ticketService = ticketService;
    }

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public String getCruiseEditPage() {
        return "cruise-edit";
    }

    @GetMapping("/description")
    @PreAuthorize("hasRole('ADMIN')")
    public String getDescriptionEditPage(Model model) {
        model.addAttribute("descriptionDTO", new CruiseDescriptionsDTO());
        return "cruise/edit-description";
    }

    @PostMapping("/descriptionEdit")
    @PreAuthorize("hasRole('ADMIN')")
    public String editCruiseDescription(@Valid @ModelAttribute("descriptionDTO") CruiseDescriptionsDTO cruiseDescriptionsDTO,
                                        BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "cruise/edit-description";
        }
        cruiseService.changeCruiseDescription(Util.getSessionCruise(session), cruiseDescriptionsDTO);
        return "cruise/edit-description";
    }

    @GetMapping("/add/ticket")
    @PreAuthorize("hasRole('ADMIN')")
    public String getAddTicketPage(@RequestParam(value = "error", required = false) String error,
                                   @RequestParam(value = "success", required = false) String success,
                                   Model model) {
        model.addAttribute("ticketDTO", new TicketDTO());
        model.addAttribute("error", error != null);
        model.addAttribute("success", success != null);
        return "cruise/add-ticket";
    }

    @PostMapping("/adding-ticket")
    @PreAuthorize("hasRole('ADMIN')")
    public String postAddTicketPage(@Valid @ModelAttribute TicketDTO ticketDTO, BindingResult bindingResult,
                                    HttpSession session) throws DataBaseDuplicateConstraint {
        if (bindingResult.hasErrors()) {
            return "cruise/add-ticket";
        }
        ticketService.addNewTicketToCruise(ticketDTO, Util.getSessionCruise(session));
        return "redirect:/cruise/edit/add/ticket?success";
    }

    @GetMapping("/all_passengers")
    @PreAuthorize("hasRole('ADMIN')")
    public String getAllPassengers(Model model, HttpSession session) {
        model.addAttribute("passengers", orderService.findAllOrdersByCruise(Util.getSessionCruise(session)));
        return "cruise/cruise-passengers";
    }

    @ExceptionHandler(DataBaseDuplicateConstraint.class)
    public String duplicateTicketHandling(DataBaseDuplicateConstraint ex) {
        log.info(ex.getMessage());
        return "redirect:/cruise/edit/add/ticket?error";
    }


}
