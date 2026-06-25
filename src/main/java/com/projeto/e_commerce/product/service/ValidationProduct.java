package com.projeto.e_commerce.product.service;

import org.springframework.stereotype.Component;

import com.projeto.e_commerce.product.exception.IdNotFoundException;
import com.projeto.e_commerce.product.exception.EntityExistException;
import com.projeto.e_commerce.product.repository.CategoryRepository;
import com.projeto.e_commerce.product.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ValidationProduct {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public void productException(String name) {
        boolean exist = productRepository.existsByName(name);
        
        if(exist) {
            throw new EntityExistException("Produto já existe");
        }
    }

    public void productIdException(Integer productId) {
        boolean exist = productRepository.existsById(productId);
        
        if(!exist) {
            throw new IdNotFoundException("Produto não encontrado");
        }
    }

    public void categoryException(String name) {
        boolean exist = categoryRepository.existsByName(name);
        if(exist) {
            throw new EntityExistException("Categoria já existe");
        }
    }
    public void categoryIdException(Integer categoryId) {
        boolean exist = categoryRepository.existsById(categoryId); 
        
        if(!exist) {
            throw new IdNotFoundException("categoria não encontrado");
        }
    }

    public void existProductByCategoryIdException(Integer categoryId) {
        boolean exist = productRepository.existsByCategory_id(categoryId); 
        
        if(!exist) {
            throw new IdNotFoundException("produto não encontrado");
        }
    }
    
}
