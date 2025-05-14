package com.antares.outlookreader.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.antares.outlookreader.dto.EmailAddressDTO;
import com.antares.outlookreader.dto.MailMessageDTO;
import com.antares.outlookreader.model.EmailAddress;
import com.antares.outlookreader.model.MailMessage;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MailMapper {

    public static MailMessageDTO toDTO(MailMessage mail) {

        MailMessageDTO dto = new MailMessageDTO();
        dto.setId(mail.getId());
        dto.setSubject(mail.getSubject());
        dto.setConversationId(mail.getConversationId());
        dto.setReceivedDateTime(mail.getReceivedDateTime());
        dto.setFrom(toDTO(mail.getFrom()));
        dto.setToRecipients(toDTOList(mail.getToRecipients()));
        dto.setCcRecipients(toDTOList(mail.getCcRecipients()));
        dto.setBody(mail.getBody());
        return dto;
    }

    private static EmailAddressDTO toDTO(EmailAddress email) {
        return new EmailAddressDTO(email.getName(), email.getAddress());
    }

    private static List<EmailAddressDTO> toDTOList(List<EmailAddress> emails) {
        return emails.stream().map(MailMapper::toDTO).collect(Collectors.toList());
    }
}