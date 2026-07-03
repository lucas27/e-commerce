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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "produtos")
@RestController
@RequestMapping("/product")
@SecurityRequirement(name = "bearerAuth")
public class ProductController {
    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @PostMapping("/products")
    @Operation(summary = "adicionar produto", description = "Método para adicionar produtos")
    @ApiResponse(responseCode = "201", description = "Produto criado com sucesso", content = @Content)
    @ApiResponse(responseCode = "409", description = "Produto já existe", content = @Content)
    @ApiResponse(responseCode = "404", description = "produto não encontrado", content = @Content)
    public ResponseEntity<String> addProducts(@RequestBody ProductDto dto) {
        String message = service.addProduct(dto);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }
    
    @PostMapping("/categories")
    @Operation(summary = "adicionar categorias", description = "Método para adicionar categorias")
    @ApiResponse(responseCode = "201", description = "Categoria criada com sucesso", content = @Content)
    @ApiResponse(responseCode = "409", description = "Categoria já existe", content = @Content)
    public ResponseEntity<String> addCategories(@RequestBody CategoryDto dto) {
        String message = service.addCategory(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @GetMapping("/products/category")
    @Operation(summary = "pegar produtos por categoria", description = "Método para pegar os produtos pela categoria")
    @ApiResponse(responseCode = "200")
    @ApiResponse(responseCode = "404", description = "produto não encontrado", content = @Content)
    public ResponseEntity<List<GetProductsDto>> getAllProductsByCategory(
        @RequestParam Integer page,
        @RequestParam Integer categoryId  
        ) {
        List<GetProductsDto> productByCategory = service.productsByCategory(page, categoryId); 
        
        return ResponseEntity.status(HttpStatus.OK).body(productByCategory);
    }

    @GetMapping("/products")
    @Operation(summary = "pegar produtos pelo nome", description = "Método para pegar os produtos pelo nome completo ou parcial")
    @ApiResponse(responseCode = "200")
    @ApiResponse(responseCode = "409", description = "Produto já existe", content = @Content)
    public ResponseEntity<List<GetProductsDto>> getAllProductsByName(
        @RequestParam String name,
        @RequestParam Integer page
    ) {
        List<GetProductsDto> productsByName = service.productsByName(name, page);

        return ResponseEntity.ok().body(productsByName);
    }

    @GetMapping("/categories")
    @Operation(summary = "pegar as categorias", description = "Método para pegar as categorias")
    @ApiResponse(responseCode = "200")
    public ResponseEntity<List<GetCategoriesDto>> getAllCategories() {
        List<GetCategoriesDto> allcategories = service.allcategories();

        return ResponseEntity.ok().body(allcategories);
    }

    @PutMapping("/products/{id}")
    @Operation(summary = "atualizar o produto", description = "Método para atualizar o produto pelo id")
    @ApiResponse(responseCode = "202", content = @Content)
    @ApiResponse(responseCode = "404", description = "produto não encontrado", content = @Content)
    public ResponseEntity<String> updateProduct(
        // não é necessário passar o ("id") quando a variavel tem o mesmo nome do campo da rota
        @PathVariable Integer id,
        @RequestBody ProductDto dto
    ) {
        String message = service.updateProduct(id, dto);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(message);
    }

    @DeleteMapping("/products/{id}")
    @Operation(summary = "deletar o produto", description = "Método para deletar o produto pelo id")
    @ApiResponse(responseCode = "200", content = @Content)
    @ApiResponse(responseCode = "404", description = "produto não encontrado", content = @Content)
    public ResponseEntity<String> deleteProduct(@PathVariable Integer id) {
        String message = service.deleteProduct(id);
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }
}
