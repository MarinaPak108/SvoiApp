package com.svoiapp.service;

import java.io.File;
import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


@Service("EmailService")
public class EmailServiceImpl implements EmailService{
    private static final String NOREPLY_ADDRESS = "noreply@svoi.com";
    private final JavaMailSender emailSender;

    public EmailServiceImpl(@Autowired JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void sendSimpleMessage(String to, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(NOREPLY_ADDRESS);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);
            System.out.println(emailSender);
            emailSender.send(message);
        } catch (MailException exception) {
            exception.printStackTrace();
        }
    }

    public Object sendConfirmationEmail (String login, String email){
        int confirmationPin = ThreadLocalRandom.current().nextInt(100000, 1000000);
        EmailServiceImpl emailService = new EmailServiceImpl(emailSender);
        try{
            emailService.sendSimpleMessage(email, "test", login+ ", your confirmation number is: " + confirmationPin);
            return confirmationPin;
        } catch (Exception e){
            return e.getMessage();
        }

    }

    @Override
    public Object sendEmail(String login, String email) {
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getInstance(prop,
                new jakarta.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("svoi.korea@gmail.com", "wmah lqiq fzfz obbd");
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("from@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(email)
            );
            message.setSubject("Testing Gmail SSL");
            message.setText("Dear Mail "+ login + " , "
                    + "\n\n Please do not spam my email!");

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void sendMessageWithAttachment(String to,
                                          String subject,
                                          String text,
                                          String pathToAttachment) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            // pass 'true' to the constructor to create a multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(NOREPLY_ADDRESS);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);

            FileSystemResource file = new FileSystemResource(new File(pathToAttachment));
            helper.addAttachment("Invoice", file);

            emailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
