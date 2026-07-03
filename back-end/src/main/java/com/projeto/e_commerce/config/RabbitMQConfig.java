package com.projeto.e_commerce.config;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfig {
    private String EXCHANGE = "topic_exchange";
    
    private String QUEUE_USER = "user.register";
    private String QUEUE_ORDER_PAYMENT = "order.canceled";
    private String QUEUE_ORDER = "order.approved";

    private String ROUTING_KEY_USER = "user.register";
    private String ROUTING_KEY_ORDER_CANCELED = "order.canceled";
    private String ROUTING_KEY_ORDER_APPROVED = "order.approved";
    
    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(EXCHANGE, true, false);
    }

    @Bean
    public Queue queueUser() {
        return new Queue(QUEUE_USER, true);
    }

    @Bean
    public Queue queueOrder() {
        return new Queue(QUEUE_ORDER_PAYMENT, true);
    }

    @Bean
    public Queue queuePayment() {
        return new Queue(QUEUE_ORDER, true);
    }

    @Bean
    public Binding bindingUser(Queue queueUser, TopicExchange exchange) {
        return BindingBuilder.bind(queueUser).to(exchange).with(ROUTING_KEY_USER);
    }

    @Bean
    public Binding bindingOrder(Queue queueOrder, TopicExchange exchange) {
        return BindingBuilder.bind(queueOrder).to(exchange).with(ROUTING_KEY_ORDER_CANCELED);
    }

    @Bean
    public Binding bindingPayment(Queue queuePayment, TopicExchange exchange) {
        return BindingBuilder.bind(queuePayment).to(exchange).with(ROUTING_KEY_ORDER_APPROVED);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new JacksonJsonMessageConverter();
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        RabbitAdmin admin = new RabbitAdmin(connectionFactory);
        admin.setAutoStartup(true);
        return admin;
    }

    @Bean
    public ApplicationRunner rabbitInit(RabbitAdmin admin) {
        return args -> admin.initialize();
    }
}
