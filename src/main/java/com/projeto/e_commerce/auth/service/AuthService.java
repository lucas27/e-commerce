package com.projeto.e_commerce.auth.service;

import org.springframework.stereotype.Service;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.projeto.e_commerce.auth.dto.LoginDto;
import com.projeto.e_commerce.auth.dto.RegisterDto;
import com.projeto.e_commerce.auth.dto.UserEventDto;
// import com.projeto.e_commerce.auth.domain.User;
import com.projeto.e_commerce.auth.entity.User;
import com.projeto.e_commerce.auth.repository.UserRepository;

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
        user.setRole(dto.role());

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
}
