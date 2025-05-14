package com.antares.outlookreader.util;

import com.antares.outlookreader.config.GraphApiProperties;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Slf4j
@Component
@RequiredArgsConstructor
public class TokenUtil {

    private final WebClient webClient;
    private final GraphApiProperties graphApiProperties;

    public Mono<String> getAccessToken() {
        log.info(">>> getAccessToken inicializado");

        var reg = graphApiProperties.getRegistration().getGraph();
        var prov = graphApiProperties.getProvider().getGraph();

        String formData = "client_id=" + reg.getClientId() +
                "&client_secret=" + reg.getClientSecret() +
                "&grant_type=" + reg.getAuthorizationGrantType() +
                "&scope=" + reg.getScope();

        return webClient.post()
                .uri(prov.getTokenUri())
                .header("Content-Type", "application/x-www-form-urlencoded")
                .bodyValue(formData)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .map(json -> {
                    String token = json.get("access_token").asText();
                    log.debug(">>> Token generado correctamente");
                    return token;
                });
    }
}