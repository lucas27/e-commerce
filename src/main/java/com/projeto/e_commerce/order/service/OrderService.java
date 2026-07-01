package com.projeto.e_commerce.order.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projeto.e_commerce.auth.entity.User;
import com.projeto.e_commerce.auth.repository.UserRepository;
import com.projeto.e_commerce.exception.IdNotFoundException;
import com.projeto.e_commerce.exception.QuantityExceededException;
import com.projeto.e_commerce.order.dto.OrderDto;
import com.projeto.e_commerce.order.dto.OrderItensDto;
import com.projeto.e_commerce.order.dto.OrderPaymentDto;
import com.projeto.e_commerce.order.entity.Order;
import com.projeto.e_commerce.order.entity.OrderItens;
import com.projeto.e_commerce.order.enums.OrderEnum;
import com.projeto.e_commerce.order.repository.OrderRepository;
import com.projeto.e_commerce.product.entity.Product;
import com.projeto.e_commerce.product.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final RabbitTemplate template;

    @Transactional
    public String addOrder(OrderDto dto, JwtAuthenticationToken auth) {
        /* não é mais necessário, mais fácil pegar logo no token do jwt*/
        // vai pegar apenas o id do usuário. 
        // Caso de erro, vai ser um DataIntegrityViolationException
        // basta configura o handler
        // sem necessidade do existById
        // confia no getReferenceById ;)
        // User userId = userRepository.getReferenceById(dto.userId().longValue());
        Long userId = ((Number) auth.getTokenAttributes().get("userId")).longValue();
        
        User user = userRepository.getReferenceById(userId);
        
        Order order = new Order();
        order.setUser(user);
        order.setStatus(OrderEnum.PENDENTE);
        
        BigDecimal totalPrice = BigDecimal.ZERO;

        // para evitar deadlock
        // ele vai reordernar pela order dos ids
        List<OrderItensDto> sortedItems = dto.orderItens().stream()
            .sorted((item1, item2) -> item1.productId().compareTo(item2.productId()))
            .toList();

        // System.out.println(order);
        for(OrderItensDto dtoItens: sortedItems) {
            OrderItens itens = new OrderItens();
            // Product product = productRepository.getReferenceById(dtoItens.productId());
            Product product = productRepository.findByIdWithLock(dtoItens.productId()).orElseThrow(() -> new IdNotFoundException("produto não encontrado"));
           
            // para testar o Lock :)
            // try {
            //     Thread.sleep(10000);
            // }catch (InterruptedException e) {
                
            // }

            if(product.getStockQuantity() < dtoItens.quantity()) {
                throw new QuantityExceededException("Estoque insuficiente");
            }
            product.setStockQuantity(product.getStockQuantity() - dtoItens.quantity());
            productRepository.save(product);

            itens.setOrder(order);
            itens.setProduct(product);
            itens.setPrice(product.getPrice());
            itens.setQuantity(dtoItens.quantity());
            
            order.addItem(itens);

            // foi a forma que eu achei para multiplicar com tipos diferentes, sem ter muitas variaveis
            BigDecimal subTotal = (product.getPrice().multiply(BigDecimal.valueOf(dtoItens.quantity())));

            // System.out.println(subTotal);
            totalPrice = totalPrice.add(subTotal);
            
        }

        order.setTotalPrice(totalPrice);
        orderRepository.save(order);
        return "pedido criado com sucesso";
    }


    public void orderToQueue(OrderPaymentDto dto) {
        if(dto.getStatus().equals(OrderEnum.PAGO)) {
            template.convertAndSend("topic_exchange", "order.approved", dto);
        } else if(dto.getStatus().equals(OrderEnum.CANCELADO)) {
            template.convertAndSend("topic_exchange", "order.canceled", dto);
        }
    }
    
    @Transactional
    public void changeOrderStatus(OrderPaymentDto dto) {
        Order order = orderRepository.getReferenceById(dto.getOrderId());
        order.setStatus(dto.getStatus());
        orderRepository.save(order);
    }
}
