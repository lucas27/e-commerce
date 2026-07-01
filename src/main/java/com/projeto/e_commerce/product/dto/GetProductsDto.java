package com.projeto.e_commerce.product.dto;

import java.math.BigDecimal;


public record GetProductsDto(
    Integer id,
    String name,
    String description,
    BigDecimal price,
    Integer stockQuantity,
    String imageUrl
) {}