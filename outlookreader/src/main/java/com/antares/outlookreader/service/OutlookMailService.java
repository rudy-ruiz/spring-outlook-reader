package com.antares.outlookreader.service;

import com.antares.outlookreader.model.EmailAddress;
import com.antares.outlookreader.model.MailMessage;
import com.antares.outlookreader.util.TokenUtil;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OutlookMailService {

    private final WebClient webClient;
    private final TokenUtil tokenUtil;

    public Mono<List<MailMessage>> fetchEmails(String userEmail) {
        String endpoint = "https://graph.microsoft.com/v1.0/users/" + userEmail + "/messages?$top=10";

        return tokenUtil.getAccessToken()
                .flatMap(token -> webClient.get()
                        .uri(endpoint)
                        .headers(h -> h.setBearerAuth(token))
                        .retrieve()
                        .bodyToMono(JsonNode.class)
                        .map(this::parseMessages)
                );
    }

    private List<MailMessage> parseMessages(JsonNode json) {
        List<MailMessage> mails = new ArrayList<>();
        for (JsonNode item : json.get("value")) {
            MailMessage mail = new MailMessage();
            mail.setId(item.get("id").asText());
            mail.setSubject(item.get("subject").asText());
            mail.setConversationId(item.get("conversationId").asText());
            mail.setReceivedDateTime(item.get("receivedDateTime").asText());
            mail.setFrom(extractEmail(item.get("from")));
            mail.setToRecipients(extractEmails(item.get("toRecipients")));
            mail.setCcRecipients(extractEmails(item.get("ccRecipients")));
            mail.setBody(item.get("body").get("content").asText());
            mails.add(mail);
        }
        return mails;
    }

    private EmailAddress extractEmail(JsonNode node) {
        if (node == null || node.get("emailAddress") == null) return null;
        JsonNode emailNode = node.get("emailAddress");
        return new EmailAddress(emailNode.get("name").asText(), emailNode.get("address").asText());
    }

    private List<EmailAddress> extractEmails(JsonNode arrayNode) {
        List<EmailAddress> list = new ArrayList<>();
        if (arrayNode != null) {
            for (JsonNode n : arrayNode) {
                EmailAddress email = extractEmail(n);
                if (email != null) list.add(email);
            }
        }
        return list;
    }
}