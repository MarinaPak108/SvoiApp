package com.svoiapp.controller.web;

import com.svoiapp.entity.DataEntity;
import com.svoiapp.exception.CustomAuthHanlder;
import com.svoiapp.formdata.CreatePinFromData;
import com.svoiapp.formdata.CreateVisaExtendFormData;
import org.springframework.http.ResponseEntity;
import com.svoiapp.service.DocService;
import com.svoiapp.service.MailService;
import com.svoiapp.service.UserService;
import org.springframework.core.io.Resource;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;
import java.util.logging.Logger;


@Controller
@RequestMapping("/m")
public class MainController {
    private final UserService service;
    private final DocService dService;
    private static final Logger logger = Logger.getLogger(MainController.class.getName());

    public MainController(UserService service, DocService dService) {
        this.service = service;
        this.dService = dService;
    }

    @GetMapping("/home")
    public String home (Model model) throws MessagingException {
        try{
            Authentication authentication =
                    SecurityContextHolder.getContext().getAuthentication();
            String[] loginEmail = authentication.getName().split("/__/");
            String login = loginEmail[0];
            if(login.equals("anonymousUser")){
                model.addAttribute("user", "Welcome to SVOI app");
                model.addAttribute("msg", "Предлагаем Вам зарегестрироваться чтобы воспользоваться услугой");

            }
            else {
                DataEntity data = service.getData(login);
                model.addAttribute("user", "Hi "+ login + ", welcome to SVOI app");
                if(data.getPin() != null && data.getConfirmed()==false){
                    model.addAttribute("formData", new CreatePinFromData());
                    model.addAttribute("msg", "Код для верификации был выслан на Ваш почтовый ящик. Также рекомендуем проверить папку 'СПАМ'.");
                }
            }
            return "home";
        }
        catch (Exception e){
            String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
            return catchExceptionToLogger(e.getMessage(),methodName);
        }
    }

    @PostMapping("/home")
    public String postHome (@ModelAttribute("formData") CreatePinFromData formData){
        try{
            Authentication authentication =
                    SecurityContextHolder.getContext().getAuthentication();
            String[] loginEmail = authentication.getName().split("/__/");
            String login = loginEmail[0];
            String pin = formData.toOnePin();
            service.checkPin(login, pin);
            return "redirect:/m/home";
        }
        catch (Exception e){
            String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
            return catchExceptionToLogger(e.getMessage(),methodName);
        }
    }

    @RequestMapping("/service")
    public String service(Model model) throws MessagingException {
        try{
            String[] loginEmail = CustomAuthHanlder.getLogin();
            String login = loginEmail[0];
            String email = loginEmail[1];
            boolean isEmailConfirmed = service.isEmailAuthorised(login, email);
            if(isEmailConfirmed){
                model.addAttribute("isChecked", true);
            }
            model.addAttribute("userName", login);
            return"service";
        }catch (Exception e) {
            String methodName = new Object() {
            }.getClass().getEnclosingMethod().getName();
            return catchExceptionToLogger(e.getMessage(), methodName);
        }
    }

    @GetMapping("/visaExt")
    public String visaExtend (Model model){
        try{
            model.addAttribute("formData", new CreateVisaExtendFormData());
            return "visa";
        }catch (Exception e) {
            String methodName = new Object() {
            }.getClass().getEnclosingMethod().getName();
            return catchExceptionToLogger(e.getMessage(), methodName);
        }
    }

    @PostMapping("/visaExt")
    public String processVisa (Model model, @ModelAttribute("formData") CreateVisaExtendFormData formData,
                               HttpServletRequest request
    ){
        try{
            Authentication authentication =
                    SecurityContextHolder.getContext().getAuthentication();
            String[] loginEmail = authentication.getName().split("/__/");
            String login = loginEmail[0];
            String visaType = request.getParameter("visatype");
            Boolean isSchool = Boolean.parseBoolean(request.getParameter("isSchool"));
            Boolean isWork = Boolean.parseBoolean(request.getParameter("isWork"));
            HashMap<String, String> data = dService.prepareEntity(formData, visaType);
            data = dService.fillSchoolWork(data,formData, visaType, isSchool, isWork);
            Boolean isSaved = dService.replaceText(data, visaType, login);
            if (isSaved){
                return "download";
            }
            else{
                model.addAttribute("formData", new CreateVisaExtendFormData());
                model.addAttribute("msg", "Что то пошло не так. Попробуйте еще раз");
                return "visa";
            }
        }catch (Exception e) {
            String methodName = new Object() {
            }.getClass().getEnclosingMethod().getName();
            return catchExceptionToLogger(e.getMessage(), methodName);
        }
    }

    @GetMapping("/download")
    public ResponseEntity<Resource> downloadFile(Model model) {
        try{
            Authentication authentication =
                    SecurityContextHolder.getContext().getAuthentication();
            String[] loginEmail = authentication.getName().split("/__/");
            String login = loginEmail[0];
            model.addAttribute("msg", "Нажмите кнопку 'Скачать' чтобы загрузить документ.");
            // Get latest file name
            String fileName = dService.getLatestFile("/Services/Visa/"+login+"/");
            // Load the file as a Resource
            Resource resource = dService.loadFileAsResource(fileName, "/Services/Visa/"+login+"/");

            // Add the resource to the model
            model.addAttribute("resource", resource);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
        }catch (Exception e) {
            String methodName = new Object() {
            }.getClass().getEnclosingMethod().getName();
            logger.info("******************* in mehtod: "+methodName+". "+e.getMessage());
            return (ResponseEntity<Resource>) ResponseEntity.EMPTY;
        }
    }

    static String catchExceptionToLogger (String msg, String methodName) {
        logger.info("******************* in mehtod: "+methodName+". "+msg);
        return "home";
    }
}