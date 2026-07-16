package com.projeto.e_commerce.auth.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.e_commerce.auth.dto.LoginDto;
import com.projeto.e_commerce.auth.dto.RegisterDto;
import com.projeto.e_commerce.auth.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.apache.tomcat.util.http.SameSiteCookies;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
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
    public ResponseEntity<String> login(@RequestBody @Valid LoginDto dto, HttpServletResponse response) {
        String token = service.authenticationLogin(dto);
        ResponseCookie cookie = ResponseCookie.from("token", token)
        .httpOnly(true) // Protege contra ataque de xss
        .secure(false)
        .path("/")
        .maxAge(86400)
        .sameSite(SameSiteCookies.LAX.toString()) // ele manda para o mesmo site, ou para o meu caso, localhost
        .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());;
        return ResponseEntity.ok().body("Login realizado");
    }

    @GetMapping("/teste")
    public String teste() {
        return "ola mundo";
    }
    
}
