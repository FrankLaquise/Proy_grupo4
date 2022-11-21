package com.example.proy_grupo4;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class Email {

    @Autowired
    private JavaMailSender email;

    public void sendEmail(String toEmail,
                          String subject,
                          String body) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("luoyujun0205@gmail.com");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);

        email.send(message);

    }
}
