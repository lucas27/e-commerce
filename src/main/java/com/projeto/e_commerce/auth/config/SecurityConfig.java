package com.projeto.e_commerce.auth.config;

import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;

import jakarta.servlet.DispatcherType;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final CreateKey key;

    public SecurityConfig(CreateKey key) {
        this.key = key;
    }

    
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.configure(http))
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(
                auth -> {
                    auth.dispatcherTypeMatchers(DispatcherType.ERROR).permitAll();
                    auth.requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll();
                    auth.requestMatchers(HttpMethod.POST, "/Auth/sign-up").permitAll();
                    auth.requestMatchers(HttpMethod.POST, "/Auth/sign-in").permitAll();
                    // devido o oauth2 usar o SCOPE_, é bom utilizar hasAuthority
                    // o hasRole, já adiciona quando for pesquisar o ROLE_
                    // caso ainda for usar o hasRole, vai ter que criar um outro bean para configurar o oauth2ResourceServer
                    auth.requestMatchers(HttpMethod.GET, "/Auth/teste").hasAuthority("SCOPE_ROLE_ADMIN");
                    auth.requestMatchers(HttpMethod.POST, "/product/**").hasAuthority("SCOPE_ROLE_ADMIN");
                    auth.anyRequest().authenticated();
                })
                // .formLogin(Customizer.withDefaults())
                // .httpBasic(Customizer.withDefaults())
                .oauth2ResourceServer(conf -> conf.jwt(Customizer.withDefaults()))
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public KeyPair keypair() throws NoSuchAlgorithmException{
        return key.createKeyasymmetric();
    }

    @Bean
    public JwtDecoder jwtDecoder(KeyPair keypair)  {
        RSAPublicKey publicKey = (RSAPublicKey) keypair.getPublic();
        return NimbusJwtDecoder.withPublicKey(publicKey).build();
    }

    @Bean
    public JwtEncoder jwtEncoder(KeyPair keypair) {
        RSAPrivateKey privateKey = (RSAPrivateKey) keypair.getPrivate();
        RSAPublicKey publicKey = (RSAPublicKey) keypair.getPublic();

        var jwk = new RSAKey.Builder(publicKey).privateKey(privateKey).build();
        var jwks = new ImmutableJWKSet<>(new JWKSet(jwk));

        return new NimbusJwtEncoder(jwks);
    }
}
