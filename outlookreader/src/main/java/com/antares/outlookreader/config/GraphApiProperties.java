package com.antares.outlookreader.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@Component
@ConfigurationProperties(prefix = "spring.security.oauth2.client")
public class GraphApiProperties {
    private Registration registration;
    private Provider provider;

    @Data
    public static class Registration {
        private Graph graph;

        @Data
        public static class Graph {
            private String clientId;
            private String clientSecret;
            private String authorizationGrantType;
            private String scope;
        }
    }

    @Data
    public static class Provider {
        private Graph graph;

        @Data
        public static class Graph {
            private String tokenUri;
        }
    }
}