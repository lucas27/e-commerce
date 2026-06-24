package com.projeto.e_commerce.product.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.e_commerce.product.dto.CategoryDto;
import com.projeto.e_commerce.product.dto.ProductDto;
import com.projeto.e_commerce.product.dto.GetCategoriesDto;
import com.projeto.e_commerce.product.dto.GetProductsDto;
import com.projeto.e_commerce.product.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    
    @PostMapping("/products")
    public ResponseEntity<String> addProducts(@RequestBody ProductDto dto) {
        String message = service.addProduct(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @PostMapping("/categories")
    public ResponseEntity<String> addCategories(@RequestBody CategoryDto dto) {
        String message = service.addCategory(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @GetMapping("/products/category")
    public List<GetProductsDto> getAllProductsByCategory(
        @RequestParam Integer page,
        @RequestParam Integer categoryId  
        ) {
        return service.productsByCategory(page, categoryId);
    }

    @GetMapping("/products")
    public List<GetProductsDto> getAllProductsByName(
        @RequestParam String name,
        @RequestParam Integer page
    ) {
        return service.productsByName(name, page);
    }

    @GetMapping("/categories")
    public List<GetCategoriesDto> getAllCategories() {
        return service.Allcategories();
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<String> updateProduct(
        // não é necessário passar o ("id") quando a variavel tem o mesmo nome do campo da rota
        @PathVariable Integer id,
        @RequestBody ProductDto dto
    ) {
        String message = service.updateProduct(id, dto);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(message);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Integer id) {
        String message = service.deleteProduct(id);
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }
}
