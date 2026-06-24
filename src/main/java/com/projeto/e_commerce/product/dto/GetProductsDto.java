package com.projeto.e_commerce.product.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;


public record GetProductsDto(
    Integer id,
    String name,
    String description,
    BigDecimal price,
    Integer stockQuantity,
    String imageUrl,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    Integer categoryId
) {}