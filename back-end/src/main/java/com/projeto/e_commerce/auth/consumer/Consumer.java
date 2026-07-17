package com.projeto.e_commerce.auth.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.projeto.e_commerce.auth.dto.UserEventDto;
import com.projeto.e_commerce.email.dto.Email;
import com.projeto.e_commerce.email.service.SendEmailService;

@Component
public class Consumer {
    private final SendEmailService send;

    public Consumer(SendEmailService send) {
        this.send = send;
    }


    @RabbitListener(queues = "user.register")
    public void Listener(UserEventDto payload) {
        try {
            String userEmail = payload.email();
            String subject = "Mensagem de cadastro";
            String text = String.format("Olá %s. \nInformamos que seu e-mail foi criado com sucesso.", payload.name());

            Email email = new Email(userEmail, subject, text); 
            Thread.sleep(3000);
            
            // System.out.println("usuário: " + payload.name() + " criado com sucesso" );
            send.sendEmail(email);
            
        }catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}
