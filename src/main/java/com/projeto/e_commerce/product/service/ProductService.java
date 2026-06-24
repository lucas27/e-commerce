package com.projeto.e_commerce.product.service;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.projeto.e_commerce.product.dto.CategoryDto;
import com.projeto.e_commerce.product.dto.ProductDto;
import com.projeto.e_commerce.product.dto.GetCategoriesDto;
import com.projeto.e_commerce.product.dto.GetProductsDto;
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

    public String addProduct(ProductDto dto) {
        Category categoryId = categoryRepository.getReferenceById(dto.categoryId());
        
        Product product = new Product();

        product.setName(dto.name());
        product.setDescription(dto.description());
        product.setPrice(dto.price());
        product.setStockQuantity(dto.stockQuantity());
        product.setImageUrl(dto.imageUrl());
        product.setCategory(categoryId);
        
        try {
            productRepository.save(product);
            return "Produto criado com sucesso";
        }catch (Exception e) {
            return e.getMessage();
        }
    }

    public String addCategory(CategoryDto dto) {
        Category category = new Category();
        category.setName(dto.name());

        try {
            categoryRepository.save(category);
            return "Categoria criada com sucesso";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public List<GetProductsDto> productsByCategory(Integer page, Integer categoryId) {
        // if(categoryId == null) {
        //     return productRepository.findAll();
        // }
        Pageable pageable = PageRequest.of(page, 10);
        return productRepository.findAllByCategory_id(categoryId, pageable);
    }

    public List<GetProductsDto> productsByName(String name, Integer page) {
        Pageable pageable = PageRequest.of(page, 10);
        return productRepository.findAllByNameContaining(name, pageable);
    }

    public List<GetCategoriesDto> Allcategories() {
        return categoryRepository.findAllCategoriesDtos();
    }

    public String updateProduct(Integer id, ProductDto dto) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("produto não encontrado"));
        
        Category categoryId = categoryRepository.getReferenceById(dto.categoryId());
        
        product.setName(dto.name());
        product.setDescription(dto.description());
        product.setPrice(dto.price());
        product.setStockQuantity(dto.stockQuantity());
        product.setImageUrl(dto.imageUrl());
        product.setCategory(categoryId);
        
        try {
            productRepository.save(product);
            return "Atualizado com sucesso";
        }catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    public void updateCategory(Integer id, String name) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("categoria não encotrado"));

        category.setName(name);
        categoryRepository.save(category);
    }

    public String deleteProduct(Integer id) {
        try {
            productRepository.deleteById(id);
            return "deletado com sucesso";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

}
