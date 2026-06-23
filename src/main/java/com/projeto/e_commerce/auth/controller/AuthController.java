package com.projeto.e_commerce.auth.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.e_commerce.auth.dto.LoginDto;
import com.projeto.e_commerce.auth.dto.RegisterDto;
import com.projeto.e_commerce.auth.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
// import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/Auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final AuthService service;
    // private final PasswordEncoder passwordEncoder;

    // public AuthController(AuthService service) {
    //     this.service = service;
    // }

    @PostMapping("/sign-up")
    @Operation(summary = "rota de criação de usuário", description = "ele manda a requisição para o rabbitmq e salva no banco de dados o usuário")
    public ResponseEntity<String> createUser(@RequestBody RegisterDto dto) {
        service.CreateUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("usuario criado");
    }
    
    @PostMapping("/sign-in")
    @Operation(summary = "rota de login", description = "ele faz a validação de login do usuário por meio do authenticationManager")
    public ResponseEntity<String> login(@RequestBody @Valid LoginDto dto) {
        String token = service.authenticationLogin(dto);
        return ResponseEntity.ok().body(token);
    }

    @GetMapping("/teste")
    public String teste() {
        return "ola mundo";
    }
    
}
