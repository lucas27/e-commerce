package com.projeto.e_commerce.product.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.projeto.e_commerce.product.dto.GetProductsDto;
import com.projeto.e_commerce.product.entity.Product;
import java.util.List;


public interface ProductRepository extends JpaRepository<Product, Integer>{
    List<GetProductsDto> findAllByNameContaining(String name, Pageable pageable);

    // @Query("FROM Product p WHERE p.category.id = :categoryId")
    // List<GetProducts> buscarPorCategoriaId( Integer categoryId);
    // Se usar o List<Product> vai ter um loop infinito por causa do relacionamento bidirecional no foreign key
    List<GetProductsDto> findAllByCategory_id(Integer categoryId, Pageable pageable);
    
    boolean existsByName(String name);

    boolean existsByCategory_id(Integer categoryId);
}
