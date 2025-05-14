package com.antares.outlookreader.controller;

import com.antares.outlookreader.dto.MailMessageDTO;
import com.antares.outlookreader.mapper.MailMapper;
import com.antares.outlookreader.service.OutlookMailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/mail")
@RequiredArgsConstructor
public class MailController {

    private final OutlookMailService outlookMailService;

    @GetMapping("/{userEmail}")
    public Mono<ResponseEntity<List<MailMessageDTO>>> getEmails(@PathVariable String userEmail) {
        log.info(">>> getEmails llamado con: {}", userEmail);

        return outlookMailService.fetchEmails(userEmail)
                .map(messages -> {
                    List<MailMessageDTO> dtos = messages.stream()
                            .map(MailMapper::toDTO)
                            .collect(Collectors.toList());
                    return ResponseEntity.ok(dtos);
                });
    }
}