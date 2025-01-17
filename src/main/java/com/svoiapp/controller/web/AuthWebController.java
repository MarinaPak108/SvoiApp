package com.svoiapp.controller.web;

import com.svoiapp.formdata.CreateLoginFromData;
import com.svoiapp.formdata.CreateUserFormData;
import com.svoiapp.service.MailService;
import com.svoiapp.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.boon.Str;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/w")
public class AuthWebController {
    private final UserService service;
    private final MailService mailService;
    public AuthWebController(UserService service, MailService mailService) {
        this.service = service;
        this.mailService = mailService;
    }

    SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();

    @RequestMapping("/logout")
    public String performLogout(Authentication authentication, HttpServletRequest request, HttpServletResponse response) {
        // .. perform logout
        this.logoutHandler.logout(request, response, authentication);
        return "redirect:/m/home";
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
        String pin;
        if (results.get(0))
            bindingResult.addError(new FieldError("errorl","login", "login "+ formData.getLogin() + " already exists. Please try again. ,"));
        if (results.get(1))
            bindingResult.addError(new FieldError("errorm","email", "email "+ formData.getEmail() + " already used. Please try with different email"));
        if(bindingResult.hasErrors())
            return "signin";
        else {
            pin = service.createUser(formData);
            if (pin != null && !pin.isEmpty()) {
                mailService.authoriseEmail(pin, formData.getLogin(), formData.getEmail());
                return "redirect:/m/home";
            }
        }
        return "signin";
    }

    @GetMapping("/access-denied")
    public String accessDenied (Model model, @RequestParam(name = "msg", required = false) int msg){
        if(msg == 1){
            model.addAttribute("msg", "Пожалуйста зарегестрируйтесь/залогиньтесь, чтобы воспользоваться услугой.");
        }
        else{
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
            else
                return "home";
        }

        return "home";
    }

    @GetMapping("/login")
    public String showLogin(@RequestParam(name = "loginError", required = false) final Boolean loginError,
                            final Model model) {
        model.addAttribute("formData", new CreateLoginFromData());
        if (loginError == Boolean.TRUE) {
            model.addAttribute("error", "wrong login or password");
        }
        return "login";}

    @GetMapping("/loginFailed")
    public String failedLogin(final Model model) {
        model.addAttribute("error", "wrong login or password");
        return "loginFailed";}



}