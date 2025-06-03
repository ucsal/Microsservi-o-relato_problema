package com.exemplo.relatoproblema.micro.model.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable() // API stateless - CSRF desnecessário
            .authorizeHttpRequests(auth -> auth
                .anyRequest().authenticated() // Todas as rotas protegidas
            )
            .oauth2ResourceServer().jwt(); // Validação JWT via OAuth2
        
        return http.build();
    }
}