package com.projeto.e_commerce.order.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.projeto.e_commerce.order.dto.OrderPaymentDto;
import com.projeto.e_commerce.order.service.OrderService;

@Component
public class OrderConsumer {
    private final OrderService service;
    
    public OrderConsumer(OrderService service) {
        this.service = service;
    }


    @RabbitListener(queues = "order.approved")
    public void OrderApprovedListener(OrderPaymentDto dto) {
        service.changeOrderStatus(dto);
        System.out.println("Compra aprovada");
    }

    @RabbitListener(queues = "order.canceled")
    public void OrderCanceledListener(OrderPaymentDto dto) {
        service.changeOrderStatus(dto);
        System.out.println("pedido cancelado");
    }
}
