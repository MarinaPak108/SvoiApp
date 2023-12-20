package com.svoiapp.controller.web;

import com.svoiapp.service.UserService;
import jakarta.mail.MessagingException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CommonController {

    public CommonController(UserService service) {
        this.service = service;
    }

    private final UserService service;

    @RequestMapping("/")
    public String homeCommon (Model model) throws MessagingException {
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
}
