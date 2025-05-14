package com.antares.outlookreader.model;

import java.util.List;

import lombok.Data;

@Data
public class MailMessage {
    private String id;
    private String subject;
    private String conversationId;
    private String receivedDateTime;
    private EmailAddress from;
    private List<EmailAddress> toRecipients;
    private List<EmailAddress> ccRecipients;
    private String body;
}
