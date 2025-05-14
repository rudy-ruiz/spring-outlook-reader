package com.antares.outlookreader;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OutlookreaderApplicationTests {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void testGetEmailsForRudy() {
        webTestClient.get()
                .uri("/api/mail/rudy@peru.pe")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.length()").isNumber()
                .jsonPath("$[0].subject").exists();
    }
}