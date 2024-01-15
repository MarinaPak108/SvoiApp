package com.svoiapp.service;
import com.svoiapp.repo.DataRepo;
import jakarta.mail.MessagingException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class MailService {

    private final DataRepo repo;
    private final EmailSender emailSender;

    public MailService(DataRepo repo, EmailSender emailSender) {
        this.repo = repo;
        this.emailSender = emailSender;
    }

    public void sendEmail(String reciever) throws MessagingException {
        emailSender.sendPlainTextEmail("svoi.korea@outlook.com",
                reciever,
                "",
                "Код для верификации электронной почты на сайте Свои в Корее: "+ String.valueOf(1234) +  " ."+ " Bведите Ваш код пройдя по ссылке: ",
                true);
    }

    @Async
    public CompletableFuture<Void> authoriseEmail (String pin, String login, String email){
        try{
            emailSender.sendPlainTextEmail("svoi.korea@outlook.com",
                    email,
                    "верификация электронной почты на сайте Свои в Корее",
                    login + ", Ваш код для верификации электронной почты на сайте Свои в Корее:  <br>"+ String.valueOf(pin) + "<br> Bведите Ваш код пройдя по ссылке: .<br>"+ "",
                    true);
            return CompletableFuture.completedFuture(null);
        }catch (Exception e){
            CompletableFuture<Void> future = new CompletableFuture<>();
            future.completeExceptionally(e);
            return future;  }
    }
}
