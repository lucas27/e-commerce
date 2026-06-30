package com.projeto.e_commerce.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.projeto.e_commerce.auth.entity.User;


public interface UserRepository extends JpaRepository<User, Long> {
    // devido ao jpa e oauth2, deve utilizar o user, e não o userDetails
    Optional<User> findUserByEmail(String username);
    boolean existsByEmail(String email);

    @Query("SELECT u.id FROM User u WHERE u.email = :email")
    Integer findOnlyIdByEmail(@Param("email") String username);
} 
