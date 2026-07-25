package com.projeto.e_commerce.auth.service;

import org.springframework.stereotype.Service;


import org.apache.tomcat.util.http.SameSiteCookies;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.projeto.e_commerce.auth.dto.LoginDto;
import com.projeto.e_commerce.auth.dto.RegisterDto;
import com.projeto.e_commerce.auth.dto.UserEventDto;
// import com.projeto.e_commerce.auth.domain.User;
import com.projeto.e_commerce.auth.entity.User;
import com.projeto.e_commerce.auth.enums.RoleEnum;
import com.projeto.e_commerce.auth.repository.UserRepository;
import com.projeto.e_commerce.exception.UnauthorizedException;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    
    private final RabbitTemplate template;
    private final UserRepository repository;
    private final JwtService service;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    // public AuthService(UserRepository userRepository, RabbitTemplate template) {
    //     this.repository = userRepository;
    //     this.template = template;
    // }
    
    public void CreateUser(RegisterDto dto) {

        if(repository.existsByEmail(dto.email())) {
            throw new RuntimeException("e-mail já existe");
        } 
        
        User user = new User();
        user.setName(dto.name());
        user.setEmail(dto.email());
        user.setPassword(passwordEncoder.encode(dto.password()));
        // uma forma de proteger o sistema
        // caso eu passa-se pelo front-end, eu iria abrir um problema enorme de segurança
        // por isso a mudança :)
        user.setRole(RoleEnum.USER);

        User saved = repository.save(user);

        UserEventDto eventPayload = new UserEventDto(saved.getId(), saved.getName(), saved.getEmail());

        template.convertAndSend("topic_exchange","user.register", eventPayload);
        // System.out.println(user);
        
    }

    public String authenticationLogin(LoginDto dto) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(dto.login(), dto.password());

        Authentication auth = authenticationManager.authenticate(authenticationToken);

        Integer userId = repository.findOnlyIdByEmail(dto.login());
        String tokenJWT = service.generateToken(auth, userId);
        
        return tokenJWT;
    }
    
    public String signIn(LoginDto dto, HttpServletResponse response) {
        String token = this.authenticationLogin(dto);
        ResponseCookie cookie = ResponseCookie.from("token", token)
        .httpOnly(true) // Protege contra ataque de xss
        .secure(false)
        .path("/")
        .maxAge(86400)
        .sameSite(SameSiteCookies.LAX.toString()) // ele manda para o mesmo site, ou para o meu caso, localhost
        .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        return "Login realizado";
    }

    public String session(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();

        if(cookies == null) {
            throw new UnauthorizedException("O cliente não está logado");
        }

        for(Cookie cookie: cookies) {
            String cookieName = cookie.getName();
            if(cookieName.contains("token")) {
                return "O cliente está logado"; 
            }else {
                throw new UnauthorizedException("O cliente não está logado");
            }   
        }
        return null;
    }

    public void logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("token", null);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        cookie.setPath("/");

        response.addCookie(cookie);
    }
}
