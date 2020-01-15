package com.rf.springsecurity.controller;

import com.rf.springsecurity.entity.cruises.Cruise;
import com.rf.springsecurity.entity.ports.Port;
import com.rf.springsecurity.exceptions.UnsupportedCruiseName;
import com.rf.springsecurity.services.CruiseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import static java.util.stream.Collectors.joining;

@Controller

public class CruiseController {

    private CruiseService cruiseService;

    @Autowired
    public CruiseController(CruiseService cruiseService) {
        this.cruiseService = cruiseService;
    }


    @GetMapping("/main")
    public String getMainPage(@RequestParam(value = "error", required = false) String error, Model model){
        model.addAttribute("error", error );
        model.addAttribute("cruises", cruiseService.getAllCruises());
        return "main";
    }


    // пробуем из мейна перекидывать сюда сразу круиз Я передал уже все круизы в мейн
    //передавать круиз в запросе
    @GetMapping("/cruise/{name}")
    public String getCruise(@PathVariable("name") String name, Model model) throws UnsupportedCruiseName{
           Cruise cruise = cruiseService.getCruiseDataByName(name);
           model.addAttribute("cruise", cruise);
           model.addAttribute("ports", cruise.getPorts().stream().map(Port::getPortName).collect(joining(",")));
        return "cruise";
    }

    @GetMapping("/test/{name}")
    public String test(@PathVariable("name") String name, Model model) throws UnsupportedCruiseName{
        Cruise cruise = cruiseService.getCruiseDataByName(name);
        model.addAttribute("cruise", cruise);
        model.addAttribute("ports", cruise.getPorts().stream().map(Port::getPortName).collect(joining(",")));
        return "cruise";
    }


}
