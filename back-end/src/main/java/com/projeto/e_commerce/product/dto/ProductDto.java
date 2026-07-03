package com.projeto.e_commerce.product.dto;

import java.math.BigDecimal;


public record ProductDto(
    String name,
    String description,
    BigDecimal price,
    Integer stockQuantity,
    String imageUrl,
    Integer categoryId
) {}
