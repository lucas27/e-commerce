package com.projeto.e_commerce.auth.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.projeto.e_commerce.auth.dto.UserEventDto;

@Component
public class Consumer {

    @RabbitListener(queues = "user.register")
    public void Listener(UserEventDto payload) {
        try {
            Thread.sleep(3000);
            
            System.out.println("usuário: " + payload.name() + " criado com sucesso" );
            
        }catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}
