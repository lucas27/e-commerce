package com.projeto.e_commerce.email.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.projeto.e_commerce.email.dto.Email;

import jakarta.validation.Valid;

@Service
@Validated
public class SendEmailService {
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    public SendEmailService(@Valid JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(Email email) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(from);
            message.setTo(email.to());
            message.setSubject(email.subject());
            message.setText(email.body());
    
            mailSender.send(message);

        }catch (Exception e) {
            System.out.println("erro:" + e.getMessage());
            System.out.println("Local:" + e.getLocalizedMessage());
        }
    }
}
