package com.svoiapp.exception;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;


import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

@Component("delegatedAuthenticationEntryPoint")
public class DelegatedAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private static Logger logger = LoggerFactory.getLogger(ErrorController.class);
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {


        Map<String, Object> data = new HashMap<>();
        data.put(
                "timestamp", Calendar.getInstance().getTime());
        data.put(
                "exception", authException.getMessage());
        logger.info("login failed. details: "+ data);
        String classs = String.valueOf(authException.getClass());
        if(classs.contains("InsufficientAuthenticationException")){
            response.sendRedirect("/w/access-denied?msg=1");
        }
        else{
            response.sendRedirect("/m/home");
        }

    }
}