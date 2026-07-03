package com.projeto.e_commerce.order.dto;

import java.util.List;

import com.projeto.e_commerce.order.enums.OrderEnum;

public record OrderDto(
    OrderEnum status,
    List<OrderItensDto> orderItens
) {}
