package com.svoiapp.controller.web;

import com.svoiapp.exception.CustomAuthHanlder;
import com.svoiapp.formdata.CreateVisaExtendFormData;
import com.svoiapp.service.DocService;
import com.svoiapp.service.MailService;
import com.svoiapp.service.UserService;
import jakarta.mail.MessagingException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;


@Controller
@RequestMapping("/m")
public class MainController {
    private final UserService service;
    private final DocService dService;
    private final MailService mailService;

    public MainController(UserService service, DocService dService, MailService mailService) {
        this.service = service;
        this.dService = dService;
        this.mailService = mailService;
    }

    @RequestMapping("/home")
    public String home (Model model) throws MessagingException {
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
    public String service(Model model) throws MessagingException {
        String[] loginEmail = CustomAuthHanlder.getLogin();
        String login = loginEmail[0];
        String email = loginEmail[1];
        boolean isEmailConfirmed = service.isEmailAuthorised(login, email);
        if(isEmailConfirmed){
            model.addAttribute("isChecked", true);
        }
        model.addAttribute("userName", login);
        return"service";
    }

    @GetMapping("/visaExt")
    public String visaExtend (Model model){
        model.addAttribute("formData", new CreateVisaExtendFormData());
        return "visa";
    }
    @PostMapping("/visaExt")
    public String processVisa (@ModelAttribute("formData") CreateVisaExtendFormData formData) throws IOException {
        dService.replaceText(formData.getName()+formData.getVisatype());
        return "service";
    }

}