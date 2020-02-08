package ua.training.cruise.controller;


import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @GetMapping("/error")
    public String getErrorPage() {
        return "404";
    }


    @Override
    public String getErrorPath() {
        return "/error";
    }
}
