package com.projeto.e_commerce.auth.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.projeto.e_commerce.auth.dto.LoginDto;
import com.projeto.e_commerce.auth.dto.RegisterDto;
import com.projeto.e_commerce.auth.entity.User;
import com.projeto.e_commerce.auth.enums.RoleEnum;
import com.projeto.e_commerce.auth.repository.UserRepository;
import com.projeto.e_commerce.exception.EntityExistException;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTeste {
    @Mock
    private UserRepository repository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private RabbitTemplate template;

    @InjectMocks
    private AuthService service;

    private RegisterDto dto;

    private User user;

    @BeforeEach
    void setUp() {
        String password = passwordEncoder.encode("12345678");

        dto = new RegisterDto("lucas", "lucas@teste.com", password ,RoleEnum.ADMIN);

        user = new User();

        user.setName(dto.name());
        user.setEmail(dto.email());
        user.setPassword(dto.password());
        user.setRole(dto.role());
    }

    @Test
    void shouldSaveUser() {
        

        when(repository.save(any(User.class))).thenReturn(user);

        service.CreateUser(dto);

        verify(repository, times(1)).save(any(User.class));
    }
}