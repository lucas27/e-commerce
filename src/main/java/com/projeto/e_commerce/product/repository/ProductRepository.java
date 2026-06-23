package com.projeto.e_commerce.product.repository;

import org.springdoc.core.converters.models.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.projeto.e_commerce.product.entity.Product;
import java.util.List;


public interface ProductRepository extends JpaRepository<Product, Integer>{
    List<Product> findAllByNameContaining(String name, Pageable pageable);
}
