package com.projeto.e_commerce.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projeto.e_commerce.product.entity.Category;

public interface CategoryRepository extends JpaRepository<Category,Integer>{
    
}
