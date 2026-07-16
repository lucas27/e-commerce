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
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.web.cors.CorsConfiguration;
// import org.springframework.web.cors.CorsConfigurationSource;
// import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
// import java.util.List;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import jakarta.servlet.DispatcherType;

@Configuration
@EnableWebSecurity
// adiciona no swagger a autorização de usar jwt para acessar as rotas
@SecurityScheme(name = "bearerAuth", type = SecuritySchemeType.HTTP, bearerFormat = "jwt", scheme = "bearer")
public class SecurityConfig {
    private final CreateKey key;

    // o requestMatchers não permite o uso do List do java.util, mas permite vetor de string
    private static final String[] SWAGGER_ROUTES = {"/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html"};

    private final static String PRODUCT_PERMIT_ROUTES [] = {
        "/product/products",
        "/product/categories",
        "/product/products/category"
    };

    public SecurityConfig(CreateKey key) {
        this.key = key;
    }

    // configurar o cors manualmente para aceitar requisições do axios,
    // mas prefire o @CrossOrigin pela facilidade
    //  @Bean
    // public CorsConfigurationSource corsConfigurationSource() {
    //     CorsConfiguration config = new CorsConfiguration();
    //     config.setAllowedOrigins(List.of("http://localhost:4200")); // Permite o Angular
    //     config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
    //     // Use "*" temporariamente para garantir que nenhum header oculto do Axios trave a requisição
    //     config.setAllowedHeaders(List.of("*")); 
    //     config.setAllowCredentials(true);

    //     UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    //     source.registerCorsConfiguration("/**", config);
    //     return source;
    // }
    
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.configure(http))
            // .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(
                auth -> {
                    auth.dispatcherTypeMatchers(DispatcherType.ERROR).permitAll();
                    auth.requestMatchers(SWAGGER_ROUTES).permitAll();
                    auth.requestMatchers(org.springframework.http.HttpMethod.OPTIONS, "/**").permitAll();
                    auth.requestMatchers(HttpMethod.POST, "/Auth/sign-up").permitAll();
                    auth.requestMatchers(HttpMethod.POST, "/Auth/sign-in").permitAll();
                    // devido o oauth2 usar o SCOPE_, é bom utilizar hasAuthority
                    // o hasRole, já adiciona quando for pesquisar o ROLE_
                    // caso ainda for usar o hasRole, vai ter que criar um outro bean para configurar o oauth2ResourceServer
                    auth.requestMatchers(HttpMethod.GET, "/Auth/teste").hasAuthority("SCOPE_ROLE_ADMIN");
                    // auth.requestMatchers("/product/**").hasAuthority("SCOPE_ROLE_ADMIN");
                    auth.requestMatchers("/order/**").hasAuthority("SCOPE_ROLE_USER");
                    auth.requestMatchers(HttpMethod.GET, PRODUCT_PERMIT_ROUTES).permitAll();
                    auth.anyRequest().authenticated();
                    // auth.anyRequest().permitAll();
                })
                // .formLogin(Customizer.withDefaults())
                // .httpBasic(Customizer.withDefaults())
                .oauth2ResourceServer(conf -> conf
                    .bearerTokenResolver(bearerTokenResolver())
                    .jwt(Customizer.withDefaults()))
                .build();
    }

    @Bean
    public BearerTokenResolver bearerTokenResolver() {
        return new CookieBearerTokenResolver("token");
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
