package com.svoiapp.controller.web;

import com.svoiapp.exception.CustomAuthHanlder;
import com.svoiapp.service.EmailService;
import com.svoiapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/m")
public class MainController {
    private final UserService service;

    @Autowired
    public EmailService emailService;

    public MainController(UserService service) {
        this.service = service;
    }

    @RequestMapping("/home")
    public String home (Model model){
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        String[] loginEmail = authentication.getName().split("/__/");
        String login = loginEmail[0];
        if(login.equals("anonymousUser")){
            model.addAttribute("user", "Welcome to SVOI app");
        }
        else {
            model.addAttribute("user", "Hi "+ login + ", welcome to SVOI app");
        }
        return "home";
    }

    @RequestMapping("/service")
    public String service(Model model){
        String[] loginEmail = CustomAuthHanlder.getLogin();
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