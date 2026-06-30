package com.projeto.e_commerce.order.dto;

import java.math.BigDecimal;
import java.util.List;

import com.projeto.e_commerce.order.enums.OrderEnum;

public record OrderDto(
    Integer userId,
    BigDecimal totalPrice,
    OrderEnum status,
    List<OrderItensDto> orderItens
) {}
