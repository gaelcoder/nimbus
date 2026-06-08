package com.bank.notification.service.impl;

import com.bank.notification.dto.NotificationRequest;
import com.bank.notification.dto.NotificationResponse;
import com.bank.notification.entity.Notification;
import com.bank.notification.enums.NotificationStatus;
import com.bank.notification.exception.NotificationNotFoundException;
import com.bank.notification.repository.NotificationRepository;
import com.bank.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl
        implements NotificationService {

    private final NotificationRepository repository;

    @Override
    public NotificationResponse sendNotification(
            NotificationRequest request
    ) {

        Notification notification =
                Notification.builder()
                        .userId(request.getUserId())
                        .destination(request.getDestination())
                        .message(request.getMessage())
                        .type(request.getType())
                        .status(NotificationStatus.SENT)
                        .build();

        return mapToResponse(
                repository.save(notification)
        );
    }

    @Override
    public NotificationResponse getById(UUID id) {

        return mapToResponse(
                repository.findById(id)
                        .orElseThrow(() ->
                                new NotificationNotFoundException(
                                        "Notification not found"
                                ))
        );
    }

    @Override
    public List<NotificationResponse> getByUserId(
            UUID userId
    ) {

        return repository.findByUserId(userId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private NotificationResponse mapToResponse(
            Notification notification
    ) {

        return NotificationResponse.builder()
                .id(notification.getId())
                .userId(notification.getUserId())
                .destination(notification.getDestination())
                .message(notification.getMessage())
                .type(notification.getType())
                .status(notification.getStatus())
                .build();
    }
}