package com.projeto.e_commerce.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.security.core.userdetails.UserDetails;

import com.projeto.e_commerce.auth.entity.User;


public interface UserRepository extends JpaRepository<User, Long> {
    // devido ao jpa e oauth2, deve utilizar o user, e não o userDetails
    Optional<User> findUserByEmail(String username);
    boolean existsByEmail(String email);
} 
