package com.svoiapp.service;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;

@Component
public class EmailSender {

    private static final Properties PROPERTIES = new Properties();
    private static final String USERNAME = "svoi.korea@outlook.com";   //change it
    private static final String PASSWORD = "29851108sejong09";   //change it
    private static final String HOST = "smtp.office365.com";

    static {
        PROPERTIES.put("mail.smtp.host", "smtp.office365.com");
        PROPERTIES.put("mail.smtp.port", "587");
        PROPERTIES.put("mail.smtp.auth", "true");
        PROPERTIES.put("mail.smtp.starttls.enable", "true");
    }

    @Async
    public CompletableFuture<Void> sendPlainTextEmail(String from, String to, String subject, String message, boolean debug) {

        Authenticator authenticator = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, PASSWORD);
            }
        };

        Session session = Session.getInstance(PROPERTIES, authenticator);
        session.setDebug(debug);

        try {

            // create a message with headers
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(from));
            InternetAddress[] address = {new InternetAddress(to)};
            msg.setRecipients(Message.RecipientType.TO, address);
            msg.setSubject(subject);
            msg.setSentDate(new Date());

            // create message body
            Multipart mp = new MimeMultipart();
            MimeBodyPart mbp = new MimeBodyPart();

            // Set content type to HTML
            mbp.setContent(message, "text/html; charset=UTF-8");

            mp.addBodyPart(mbp);
            msg.setContent(mp);

            // send the message
            Transport.send(msg);

            return CompletableFuture.completedFuture(null);

        } catch (MessagingException mex) {
            mex.printStackTrace();
            Exception ex = null;
            if ((ex = mex.getNextException()) != null) {
                ex.printStackTrace();
            }
            return CompletableFuture.failedFuture(mex);
        }
    }
}