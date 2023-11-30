package com.svoiapp.controller.web;

import com.svoiapp.service.EmailService;
import com.svoiapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Controller
@RequestMapping("/w_main")
public class MainController {
    private final UserService service;

    @Autowired
    public EmailService emailService;

    public MainController(UserService service) {
        this.service = service;
    }

    @RequestMapping("/service")
    public String service(Model model){
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        String[] loginEmail = authentication.getName().split("/__/");
        String login = loginEmail[0];
        String email = loginEmail[1];
        boolean isEmailConfirmed = service.isEmailAuthorised(login, email);
        if(isEmailConfirmed){
            model.addAttribute("isChecked", true);
        }
        else {
            Object result = emailService.sendConfirmationEmail(login, email);
            if(result instanceof Integer){
                service.authoriseEmail((Integer) result, login, email);
            }
        }
        model.addAttribute("userName", login);
        return"service";
    }

}
