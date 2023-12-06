package com.svoiapp.exception;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CustomAccessDeniedHandler implements AccessDeniedHandler{
    private static Logger logger = LoggerFactory.getLogger(ErrorController.class);

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        Map<String, Object> data = new HashMap<>();
        String[] loginemail =  CustomAuthHanlder.getLogin();
        data.put(
                "timestamp", Calendar.getInstance().getTime());
        data.put(
                "error", accessDeniedException.getMessage());
        if( loginemail != null){
            String login = loginemail[0];
            logger.info("access for " + login + " denied. details: " + data);
        }
        else
            logger.info("login not avaliable. access denied. details: " + data);
        response.sendRedirect("/w/access-denied");
    }
}
