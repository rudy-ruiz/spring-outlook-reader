package com.antares.outlookreader.dto;

import java.util.List;

import lombok.Data;

@Data
public class MailMessageDTO {
    private String id;
    private String subject;
    private String conversationId;
    private String receivedDateTime;
    private EmailAddressDTO from;
    private List<EmailAddressDTO> toRecipients;
    private List<EmailAddressDTO> ccRecipients;
    private String body;
}
