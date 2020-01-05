package com.rf.springsecurity.controller;

import com.rf.springsecurity.domain.cruises.Cruise;
import com.rf.springsecurity.domain.ports.Port;
import com.rf.springsecurity.exceptions.UnsupportedCruiseName;
import com.rf.springsecurity.services.CruiseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import static java.util.stream.Collectors.joining;

@Controller
public class CruiseController {

    private CruiseService cruiseService;

    @Autowired
    public CruiseController(CruiseService cruiseService) {
        this.cruiseService = cruiseService;
    }


    @GetMapping("/main")
    public String getMainPage(Model model){
        model.addAttribute("cruises", cruiseService.getAllCruises());
        return "main";
    }

    //TODO exception handling
    @GetMapping("/cruise/{name}")
    public String getCruise(@PathVariable("name") String name, Model model) throws UnsupportedCruiseName{
           Cruise cruise = cruiseService.getCruiseDataByName(name);
           model.addAttribute("name", name);
           model.addAttribute("ports", cruise.getPorts().stream().map(Port::getPortName).collect(joining(",")));
           model.addAttribute("departureDate",cruise.getDepartureDate());
        return "cruise";
    }
}
