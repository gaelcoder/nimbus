package com.bank.notification.dto;

import com.bank.notification.enums.NotificationStatus;
import com.bank.notification.enums.NotificationType;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationResponse {

    private UUID id;

    private UUID userId;

    private String destination;

    private String message;

    private NotificationType type;

    private NotificationStatus status;
}