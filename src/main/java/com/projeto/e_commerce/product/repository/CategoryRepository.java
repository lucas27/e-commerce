package com.projeto.e_commerce.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.projeto.e_commerce.product.dto.GetCategoriesDto;
import com.projeto.e_commerce.product.entity.Category;

public interface CategoryRepository extends JpaRepository<Category,Integer>{
    @Query("SELECT new com.projeto.e_commerce.product.dto.GetCategoriesDto(c.id, c.name) FROM Category c")
    List<GetCategoriesDto> findAllCategoriesDtos();
}
