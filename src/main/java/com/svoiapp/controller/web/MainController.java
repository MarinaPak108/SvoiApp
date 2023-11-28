package com.svoiapp.controller.web;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/w_main")
public class MainController {
    @GetMapping("/service")
    public String service(){
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        System.out.println(login);
        return"service";
    }

}
