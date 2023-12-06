package com.svoiapp.service;


public interface EmailService {
    void sendSimpleMessage(String to,
                           String subject,
                           String text);
    void sendMessageWithAttachment(String to,
                                   String subject,
                                   String text,
                                   String pathToAttachment);
    Object sendConfirmationEmail (String login, String email);

    Object sendEmail (String login, String email);
}
