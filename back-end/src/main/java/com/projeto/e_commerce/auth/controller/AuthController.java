package com.projeto.e_commerce.auth.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.e_commerce.auth.dto.LoginDto;
import com.projeto.e_commerce.auth.dto.RegisterDto;
import com.projeto.e_commerce.auth.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
import org.springframework.web.bind.annotation.CrossOrigin;

@Tag(name="auth")
@RestController
@RequestMapping("/Auth")
@RequiredArgsConstructor
@SecurityRequirement(name="bearerAuth")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class AuthController {
    
    private final AuthService service;

    @PostMapping("/sign-up")
    @Operation(summary = "rota de criação de usuário", description = "ele recebe a requisição e salva no banco de dados com cadastro do usuário, e no final manda um e-mail de notificação para o usuário.")
    public ResponseEntity<String> createUser(@RequestBody RegisterDto dto) {
        service.CreateUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("usuario criado");
    }
    
    @PostMapping("/sign-in")
    @Operation(summary = "rota de login", description = "ele faz a validação de login do usuário por meio do authenticationManager")
    public ResponseEntity<String> login(@RequestBody @Valid LoginDto dto, HttpServletResponse response) {
        String message = service.signIn(dto, response);
        return ResponseEntity.ok().body(message);
    }

    @GetMapping("/validate")
    @Operation(summary = "rota de validação de conta", description = "ele faz a validação de login do usuário, verificando se existe um cookie com o jwt")
    @ApiResponse(responseCode = "200", description = "O cliente está logado")
    @ApiResponse(responseCode = "401", description = "O cliente não está logado")
    public ResponseEntity<String> validateSession(HttpServletRequest request) {
        String message = service.session(request);
        return  ResponseEntity.status(HttpStatus.OK).body(message);  
    }
    
    @PostMapping("/logout")
    @Operation(summary = "rota de deslogar da conta", description = "ele remove o cookie com token jwt, fazendo o deslogamento da conta")
    @ApiResponse(responseCode = "200", description = "deslogado")
    public ResponseEntity<String> logout(HttpServletResponse response) {
        service.logout(response);
        return ResponseEntity.ok().body("deslogado");
    }
}
