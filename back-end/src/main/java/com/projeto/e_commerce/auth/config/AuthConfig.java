package com.projeto.e_commerce.auth.config;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.projeto.e_commerce.auth.entity.User;
import com.projeto.e_commerce.auth.entity.UserAuthenticated;
import com.projeto.e_commerce.auth.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthConfig implements UserDetailsService{
    private final UserRepository repository;

    // devido ao jpa e oauth2, tem que manter dessa forma
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findUserByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new UserAuthenticated(user);
    }
    
}
