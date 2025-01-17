package com.svoiapp.exception;

import com.svoiapp.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    private static Logger logger = LoggerFactory.getLogger(CustomAuthenticationFailureHandler.class.getName());
    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception)
            throws IOException, ServletException {

        //response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        Map<String, Object> data = new HashMap<>();
        data.put(
                "timestamp", Calendar.getInstance().getTime());
        data.put(
                "exception", exception.getMessage());
        logger.info("login failed. details: "+ data);
        //response.sendRedirect("/w/login?loginError=true");
        response.sendRedirect("/w/loginFailed");
    }
}
