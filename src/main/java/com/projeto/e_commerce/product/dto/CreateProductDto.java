package com.projeto.e_commerce.product.dto;

import java.math.BigDecimal;


public record CreateProductDto(
    String name,
    String description,
    BigDecimal price,
    Integer stockQuantity,
    String imageUrl,
    Integer categoryId
) {}
