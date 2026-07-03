package com.projeto.e_commerce.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projeto.e_commerce.order.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Integer>{
    boolean existsByUser_id(Integer userId);
}
