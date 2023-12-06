package com.svoiapp.controller.web;

import com.svoiapp.formdata.CreateLoginFromData;
import com.svoiapp.formdata.CreateUserFormData;
import com.svoiapp.formdata.form.LoginForm;
import com.svoiapp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;

import java.util.List;

@Controller
@RequestMapping("/w")
public class UserWebController {
    private final UserService service;

    public UserWebController(UserService service) {
        this.service = service;
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

    @GetMapping("/access-denied")
    public String accessDenied (Model model){
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        String[] loginEmail = authentication.getName().split("/__/");
        String login = loginEmail[0];
        String email = loginEmail[1];
        boolean isEmailConfirmed = service.isEmailAuthorised(login, email);
        String role = authentication.getAuthorities().iterator().next().getAuthority();
        if(!isEmailConfirmed && role.equals("ROLE_USER")){
            model.addAttribute("msg", "please confirm your email");

        }
        else if (role.equals("ROLE_GUEST")){
            model.addAttribute("msg", "please sign in and confirm email to use our services");
        }
        return "home";
    }

    @GetMapping("/login")
    public String showLogin(@RequestParam(name = "loginError", required = false) final Boolean loginError,
                            @RequestParam(name = "loginDetails", required = false) final Boolean loginDetails,
                            final Model model) {
        model.addAttribute("formData", new CreateLoginFromData());
        if (loginError == Boolean.TRUE) {
            model.addAttribute("error", "worng login or password");
        }
        return "login";}



}