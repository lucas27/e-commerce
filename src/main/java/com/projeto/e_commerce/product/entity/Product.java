package com.projeto.e_commerce.product.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="Products")
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String description;

    // para evitar problemas de arredondamento e erros de ponto flutuante do float e do double
    @Column(precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name="stock_quantity")
    private Integer stockQuantity;

    // para salvar a imagem de um produto
    @Column(name="image_url")
    private String imageUrl;

    @CreationTimestamp
    @Column(name="created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name="updated_at", updatable = true)
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name="category_id", nullable = false)
    private Category category;
}
