package com.bank.notification.controller;

import com.bank.notification.dto.NotificationRequest;
import com.bank.notification.dto.NotificationResponse;
import com.bank.notification.service.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService service;

    @PostMapping
    public ResponseEntity<NotificationResponse>
    sendNotification(
            @Valid @RequestBody
            NotificationRequest request
    ) {

        return ResponseEntity.ok(
                service.sendNotification(request)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificationResponse>
    getById(
            @PathVariable UUID id
    ) {

        return ResponseEntity.ok(
                service.getById(id)
        );
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<NotificationResponse>>
    getByUserId(
            @PathVariable UUID userId
    ) {

        return ResponseEntity.ok(
                service.getByUserId(userId)
        );
    }
}