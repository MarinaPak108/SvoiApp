package com.svoiapp.controller.web;

import com.svoiapp.entity.DataEntity;
import com.svoiapp.service.UserService;
import jakarta.mail.MessagingException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CommonController {

    @RequestMapping("/")
    public String homeCommon () {
        return "redirect:/m/home";
    }
}
