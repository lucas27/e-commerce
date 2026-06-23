package com.projeto.e_commerce.product.service;

import org.springframework.stereotype.Service;

import com.projeto.e_commerce.product.dto.CreateCategoryDto;
import com.projeto.e_commerce.product.dto.CreateProductDto;
import com.projeto.e_commerce.product.entity.Category;
import com.projeto.e_commerce.product.entity.Product;
import com.projeto.e_commerce.product.repository.CategoryRepository;
import com.projeto.e_commerce.product.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public String addProduct(CreateProductDto dto) {
        Category categoryId = categoryRepository.getReferenceById(dto.categoryId());
        
        Product product = new Product();

        product.setName(dto.name());
        product.setDescription(dto.description());
        product.setPrice(dto.price());
        product.setStockQuantity(dto.stockQuantity());
        product.setImageUrl(dto.imageUrl());
        product.setCategoryId(categoryId);
        
        try {
            productRepository.save(product);
            return "Produto criado com sucesso";
        }catch (Exception e) {
            return e.getMessage();
        }
    }

    public String addCategory(CreateCategoryDto dto) {
        Category category = new Category();
        category.setName(dto.name());

        try {
            categoryRepository.save(category);
            return "Categoria criada com sucesso";
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
