package com.antares.outlookreader.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class GraphApiConfig {

    @Bean
    public WebClient webClient() {
         log.info("SLF4J: webClient inicializado");
        return WebClient.builder().build(); // sin baseUrl
    }
}