package com.svoiapp.exception;

import com.svoiapp.service.UserService;
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
    private static Logger logger = LoggerFactory.getLogger(DelegatedAuthenticationEntryPoint.class.getName());
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {


        Map<String, Object> data = new HashMap<>();
        data.put(
                "timestamp", Calendar.getInstance().getTime());
        data.put(
                "exception", authException.getMessage());

        String classs = String.valueOf(authException.getClass());
        if(classs.contains("InsufficientAuthenticationException")){
            logger.warn ("login failed. details: "+ data);
            response.sendRedirect("/w/access-denied?msg=1");
        }
        else{
            logger.info("details: "+ data);
            response.sendRedirect("/m/home");
        }

    }
}