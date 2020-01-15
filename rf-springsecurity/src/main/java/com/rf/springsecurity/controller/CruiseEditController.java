package com.rf.springsecurity.controller;

import com.rf.springsecurity.dto.CruiseDescriptionsDTO;
import com.rf.springsecurity.dto.TicketDTO;
import com.rf.springsecurity.exceptions.UnsupportedCruiseName;
import com.rf.springsecurity.services.CruiseService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/cruise/{name}/edit")
public class CruiseEditController {
    //TODO null from form editing
    //TODO same tickets(name and cruise_id) in one table
    private final CruiseService cruiseService;

    public CruiseEditController(CruiseService cruiseService) {
        this.cruiseService = cruiseService;
    }

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public String getCruiseEditPage(@PathVariable("name") String name){
        return "cruise-edit";
    }

    @GetMapping("/description")
    @PreAuthorize("hasRole('ADMIN')")
    public String getDescriptionEditPage(@PathVariable("name") String name, Model model) throws UnsupportedCruiseName {
        model.addAttribute("cruise",cruiseService.getCruiseDataByName(name));
        model.addAttribute("descriptionDTO", new CruiseDescriptionsDTO());
        return "cruise/edit-description";
    }

    @PostMapping("/descriptionEdit")
    @PreAuthorize("hasRole('ADMIN')")
    public String editCruiseDescription(@PathVariable("name") String  name,
                                        @ModelAttribute("descriptionDTO") CruiseDescriptionsDTO cruiseDescriptionsDTO) throws UnsupportedCruiseName {
        //TODO обернуть  єтот метод
        cruiseService.changeCruiseDescription(cruiseService.getCruiseDataByName(name), cruiseDescriptionsDTO);
        return "cruise/edit-description";
    }

    @GetMapping("/add/ticket")
    @PreAuthorize("hasRole('ADMIN')")
    public String getAddTicketPage(@PathVariable("name") String name,
                                   TicketDTO ticketDTO, Model model) throws UnsupportedCruiseName {
        model.addAttribute("cruise",cruiseService.getCruiseDataByName(name));
        model.addAttribute("ticketDTO", ticketDTO);
        return "cruise/add-ticket";
    }

    @PostMapping("/adding-ticket")
    @PreAuthorize("hasRole('ADMIN')")
    public String getAddTicketPage(@PathVariable("name") String name,
                                   @Valid @ModelAttribute TicketDTO ticketDTO,
                                   BindingResult bindingResult) throws UnsupportedCruiseName {
        if(bindingResult.hasErrors()){
            return "cruise/add-ticket";
        }
        cruiseService.addNewTicketToCruise(cruiseService.getCruiseDataByName(name), ticketDTO);
        return "cruise/add-ticket";
    }

    @GetMapping("/all_passengers")
    @PreAuthorize("hasRole('ADMIN')")
    public String getAllPassengers(@PathVariable("name") String name, Model model) throws UnsupportedCruiseName {
        model.addAttribute("passengers", cruiseService.getCurrentListOfPassengersAtCruise(name));
        return "cruise/cruise-passengers";
    }

}
