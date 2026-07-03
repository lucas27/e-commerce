package com.projeto.e_commerce.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projeto.e_commerce.order.entity.OrderItens;

public interface OrderItensRepository extends JpaRepository<OrderItens, Integer>{
    
}
