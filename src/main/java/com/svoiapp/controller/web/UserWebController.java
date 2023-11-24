package com.svoiapp.controller.web;

import com.svoiapp.formdata.CreateLoginFromData;
import com.svoiapp.formdata.CreateUserFormData;
import com.svoiapp.formdata.form.LoginForm;
import com.svoiapp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/w")
public class UserWebController {
    private final UserService service;

    public UserWebController(UserService service) {
        this.service = service;
    }

    @GetMapping("/home")
    public String home (){
        System.out.println("here");
        return "home";
    }

    @GetMapping("/signin")
    public String sign (Model model) {
        model.addAttribute("formData", new CreateUserFormData());
        return "signin"; }
    @PostMapping("/signin")
    public String singed (@Valid @ModelAttribute("formData") CreateUserFormData formData,
                          BindingResult bindingResult,
                          Model model){
        List<Boolean> results = service.isLoginEmailExists(formData.getLogin(), formData.getEmail());

        if (results.get(0))
            bindingResult.addError(new FieldError("errorl","login", "login "+ formData.getLogin() + " already exists. Please try again. ,"));
        if (results.get(1))
            bindingResult.addError(new FieldError("errorm","email", "email "+ formData.getEmail() + " already used. Please try with different email"));
        if(bindingResult.hasErrors())
            return "signin";
        else
            return "mypage";
    }

    @GetMapping("/login")
    public String showLogin(Model model) {
        model.addAttribute("formData", new CreateLoginFromData());
        return "login";}

}
