package com.svoiapp.service;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Properties;

@Service
public class MailService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendHtmlEmail() throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();

        message.setFrom(new InternetAddress("svoi.korea@gmail.com"));
        message.setRecipients(MimeMessage.RecipientType.TO, "pakmarina108@gmail.com");
        message.setSubject("Test email from Spring");

        String htmlContent = "<h1>This is a test Spring Boot email</h1>" +
                "<p>It can contain <strong>HTML</strong> content.</p>";
        message.setContent(htmlContent, "text/html; charset=utf-8");

        mailSender.send(message);
    }
}
