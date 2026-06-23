package com.projeto.e_commerce.product.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.e_commerce.product.dto.CreateCategoryDto;
import com.projeto.e_commerce.product.dto.CreateProductDto;
import com.projeto.e_commerce.product.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    
    @PostMapping("/products")
    public ResponseEntity<String> addProducts(@RequestBody CreateProductDto dto) {
        String message = service.addProduct(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @PostMapping("/categories")
    public ResponseEntity<String> addCategories(@RequestBody CreateCategoryDto dto) {
        String message = service.addCategory(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }
}
