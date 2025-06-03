package com.exemplo.relatoproblema.micro.model.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    
    // Cliente HTTP reativo para comunicação entre microserviços
    @Bean
    public WebClient webClient() {
        return WebClient.builder().build();
    }
}