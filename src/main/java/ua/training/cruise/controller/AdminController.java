package ua.training.cruise.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.training.cruise.dto.CruiseDescriptionsDTO;
import ua.training.cruise.entity.cruise.Cruise;
import ua.training.cruise.entity.cruise.Ticket;
import ua.training.cruise.exception.DataBaseDuplicateConstraint;
import ua.training.cruise.service.CruiseService;
import ua.training.cruise.service.OrderService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static ua.training.cruise.controller.SessionAttributeConstants.SESSION_CRUISE;

@Slf4j
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
    public String editCruiseDescription(@ModelAttribute("descriptionDTO") CruiseDescriptionsDTO cruiseDescriptionsDTO, HttpSession session) {
        cruiseService.changeCruiseDescription((Cruise) session.getAttribute(SESSION_CRUISE), cruiseDescriptionsDTO);
        return "cruise/edit-description";
    }

    @GetMapping("/add/ticket")
    @PreAuthorize("hasRole('ADMIN')")
    public String getAddTicketPage(@RequestParam(value = "error", required = false) String error,
                                   @RequestParam(value = "success", required = false) String success,
                                   Model model) {
        model.addAttribute("ticketDTO", new Ticket());
        model.addAttribute("error", error != null);
        model.addAttribute("success", success != null);
        return "cruise/add-ticket";
    }

    @PostMapping("/adding-ticket")
    @PreAuthorize("hasRole('ADMIN')")
    public String getAddTicketPage(@Valid @ModelAttribute Ticket ticketDTO,
                                   HttpSession session) throws DataBaseDuplicateConstraint {
        cruiseService.addNewTicketToCruise(ticketDTO, (Cruise) session.getAttribute(SESSION_CRUISE));
        return "redirect:/cruise/edit/add/ticket?success";
    }

    @GetMapping("/all_passengers")
    @PreAuthorize("hasRole('ADMIN')")
    public String getAllPassengers(Model model, HttpSession session) {
        model.addAttribute("passengers", orderService.findAllOrdersByCruise((Cruise) session.getAttribute(SESSION_CRUISE)));
        return "cruise/cruise-passengers";
    }

    @ExceptionHandler(DataBaseDuplicateConstraint.class)
    public String duplicateTicketHandling(DataBaseDuplicateConstraint ex) {
        log.info(ex.getMessage());
        return "redirect:/cruise/edit/add/ticket?error";
    }


}
