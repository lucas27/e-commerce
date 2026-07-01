package com.projeto.e_commerce.order.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.e_commerce.order.dto.OrderDto;
import com.projeto.e_commerce.order.dto.OrderPaymentDto;
import com.projeto.e_commerce.order.enums.OrderEnum;
import com.projeto.e_commerce.order.service.OrderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name="Order")
@RestController
@RequestMapping("/order")
@SecurityRequirement(name = "bearerAuth")
public class OrderController {
    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping("/orders")
    @Operation(summary = "criar pedido", description = "cria o pedido e salva no banco de dados")
    @ApiResponse(responseCode = "201", description = "pedido criado com sucesso")
    @ApiResponse(responseCode = "409")
    public ResponseEntity<String> createOrder(@RequestBody OrderDto dto, @Valid JwtAuthenticationToken authToken) {
        // authToken.getTokenAttributes()
        // System.out.println(authToken.getToken().getClaims().get("userId"));
        
        String message = service.addOrder(dto, authToken);
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @PutMapping("/{orderId}/payment-approve")
    @Operation(summary = "aprovar o pagamento do pedido", description = "ele vai ser enviado para rabbitMq é vai aprovar o pedido")
    @ApiResponse(responseCode = "200", description = "pedido aprovado")
    public ResponseEntity<String> orderApproved(@PathVariable Integer orderId) {
        OrderPaymentDto dto = new OrderPaymentDto();
        dto.setOrderId(orderId);
        dto.setStatus(OrderEnum.PAGO);
        service.orderToQueue(dto);
        return ResponseEntity.ok().body("pedido aprovado");
    }

    @PutMapping("/{orderId}/order-cancel")
    @Operation(summary = "cancelar o pedido", description = "ele vai ser enviado para rabbitMq é vai cancelar o pedido")
    @ApiResponse(responseCode = "200", description = "pedido cancelado")
    public ResponseEntity<String> orderCanceled(@PathVariable Integer orderId) {
        OrderPaymentDto dto = new OrderPaymentDto();
        dto.setOrderId(orderId);
        dto.setStatus(OrderEnum.CANCELADO);
        service.orderToQueue(dto);
        return ResponseEntity.ok().body("pedido cancelado");
    }
}
