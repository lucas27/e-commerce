package com.projeto.e_commerce.auth.service;

import java.time.Instant;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
    private final JwtEncoder encoder;

    public JwtService(JwtEncoder encoder) {
        this.encoder = encoder;
    }

    public String generateToken(Authentication authentication, Integer userId) {
        Instant now = Instant.now();
        long expiry = 86400L;

        // ele pega do getAuthorities() as roles passadas no UserAuthenticated, e faz todo o mapeamento pra pegar as ROLE_
        String scope = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(" "));

        // System.out.print(authentication);
        var claims = JwtClaimsSet.builder()
            // diz quem emitiu o jwt
            .issuer("spring-security-jwt")
            // data de criação
            .issuedAt(now)
            .expiresAt(now.plusSeconds(expiry))
            .subject(authentication.getName())
            .claim("scope", scope)
            .claim("userId", userId)
            .build();

        return encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
