package com.projeto.e_commerce.product.service;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.projeto.e_commerce.exception.IdNotFoundException;
import com.projeto.e_commerce.product.dto.CategoryDto;
import com.projeto.e_commerce.product.dto.ProductDto;
import com.projeto.e_commerce.product.dto.GetCategoriesDto;
import com.projeto.e_commerce.product.dto.GetProductsDto;
import com.projeto.e_commerce.product.entity.Category;
import com.projeto.e_commerce.product.entity.Product;
import com.projeto.e_commerce.product.repository.CategoryRepository;
import com.projeto.e_commerce.product.repository.ProductRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ValidationProduct validation;

    // usar em inserções, atualizações ou deleções
    @Transactional
    public String addProduct(ProductDto dto) {
        validation.productException(dto.name());

        Category categoryId = categoryRepository.getReferenceById(dto.categoryId());
        
        Product product = new Product();

        product.setName(dto.name());
        product.setDescription(dto.description());
        product.setPrice(dto.price());
        product.setStockQuantity(dto.stockQuantity());
        product.setImageUrl(dto.imageUrl());
        product.setCategory(categoryId);
        
        productRepository.save(product);
        return "Produto criado com sucesso"; 
    }

    @Transactional
    public String addCategory(CategoryDto dto) {
        validation.categoryException(dto.name());

        Category category = new Category();
        category.setName(dto.name());
        
        categoryRepository.save(category);
        return "Categoria criada com sucesso";
    }

    public List<GetProductsDto> productsByCategory(Integer page, Integer categoryId) {
        Pageable pageable = PageRequest.of(page, 10);

        validation.existProductByCategoryIdException(categoryId);
        
        return productRepository.findAllByCategory_id(categoryId, pageable);
    }

    public List<GetProductsDto> productsByName(String name, Integer page) {
        Pageable pageable = PageRequest.of(page, 10);

        validation.productException(name);

        return productRepository.findAllByNameContaining(name, pageable);
    }

    public List<GetCategoriesDto> allcategories() {
        return categoryRepository.findAllCategoriesDtos();
    }

    @Transactional
    public String updateProduct(Integer id, ProductDto dto) {
        Product product = productRepository.findById(id).orElseThrow(() -> new IdNotFoundException("produto não encontrado"));
        
        Category categoryId = categoryRepository.getReferenceById(dto.categoryId());
        
        product.setName(dto.name());
        product.setDescription(dto.description());
        product.setPrice(dto.price());
        product.setStockQuantity(dto.stockQuantity());
        product.setImageUrl(dto.imageUrl());
        product.setCategory(categoryId);
        
        productRepository.save(product);
        return "Atualizado com sucesso";
    }

    @Transactional
    public void updateCategory(Integer id, String name) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new IdNotFoundException("categoria não encotrado"));

        category.setName(name);
        categoryRepository.save(category);
    }

    public String deleteProduct(Integer id) {
        validation.productIdException(id);
        
        productRepository.deleteById(id);
        return "deletado com sucesso";
    }
}
