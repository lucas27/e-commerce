package com.projeto.e_commerce.auth.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.projeto.e_commerce.auth.enums.RoleEnum;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
// import lombok.AllArgsConstructor;
import lombok.Data;
// import lombok.NoArgsConstructor;

@Entity
//user é uma palavra reservada no PostgreSQL
@Table(name="users")
@Data
// @AllArgsConstructor
// @NoArgsConstructor
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String email;
    private String password;
    
    @Enumerated(EnumType.STRING)
    private RoleEnum role;
    
    @CreationTimestamp
    private LocalDateTime createdAt;

}
