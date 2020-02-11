package ua.training.cruise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.training.cruise.controller.util.Util;
import ua.training.cruise.exception.NotFoundExcursion;
import ua.training.cruise.service.ExcursionService;

import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/user")
public class ExcursionController {

    private final ExcursionService excursionService;

    @Autowired
    public ExcursionController(ExcursionService excursionService) {
        this.excursionService = excursionService;
    }


    @PostMapping(value = "/add/excursion")
    public String addExcursion(@ModelAttribute("id") Long id, HttpSession session) throws NotFoundExcursion {
        Util.getSessionOrder(session).getExcursions().add(excursionService.findById(id));
        return "redirect:/user/cruise/buy-submit";
    }

    @PostMapping(value = "/remove/excursion")
    public String removeExcursion(@ModelAttribute("id") Long id, HttpSession session) throws NotFoundExcursion {
        Util.getSessionOrder(session).getExcursions().remove(excursionService.findById(id));
        return "redirect:/user/cruise/buy-submit";
    }

    @GetMapping("/cruise/buy-submit")
    public String submitBuyPage(Model model, HttpSession session) {
        model.addAttribute("excursions", excursionService.getAllExcursionsByCruiseId(Util.getSessionCruise(session).getId()));
        Util.getSessionOrder(session).setOrderPrice(Util.calcOrderTotalPrice(session));
        return "submit-form";
    }
}
