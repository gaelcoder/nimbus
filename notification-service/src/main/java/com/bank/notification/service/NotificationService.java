package com.bank.notification.service;

import com.bank.notification.dto.NotificationRequest;
import com.bank.notification.dto.NotificationResponse;

import java.util.List;
import java.util.UUID;

public interface NotificationService {

    NotificationResponse sendNotification(
            NotificationRequest request
    );

    NotificationResponse getById(UUID id);

    List<NotificationResponse> getByUserId(UUID userId);
}