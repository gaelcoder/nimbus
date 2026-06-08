package com.bank.notification.dto;

import com.bank.notification.enums.NotificationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRequest {

    @NotNull
    private UUID userId;

    @NotBlank
    private String destination;

    @NotBlank
    private String message;

    @NotNull
    private NotificationType type;
}