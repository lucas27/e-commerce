package com.projeto.e_commerce.order.dto;

import java.math.BigDecimal;

public record OrderItensDto(
    Integer orderId,
    Integer productId,
    Integer quantity,
    BigDecimal price
) {}