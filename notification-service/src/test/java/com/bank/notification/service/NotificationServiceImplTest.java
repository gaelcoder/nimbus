package com.bank.notification.service;

import com.bank.notification.dto.NotificationRequest;
import com.bank.notification.dto.NotificationResponse;
import com.bank.notification.entity.Notification;
import com.bank.notification.enums.NotificationStatus;
import com.bank.notification.enums.NotificationType;
import com.bank.notification.exception.NotificationNotFoundException;
import com.bank.notification.repository.NotificationRepository;
import com.bank.notification.service.impl.NotificationServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationServiceImplTest {

    @Mock
    private NotificationRepository repository;

    @InjectMocks
    private NotificationServiceImpl service;

    @Test
    void shouldSendNotification() {

        UUID userId = UUID.randomUUID();

        NotificationRequest request =
                NotificationRequest.builder()
                        .userId(userId)
                        .destination("user@email.com")
                        .message("Transfer completed")
                        .type(NotificationType.EMAIL)
                        .build();

        Notification notification =
                Notification.builder()
                        .id(UUID.randomUUID())
                        .userId(userId)
                        .destination("user@email.com")
                        .message("Transfer completed")
                        .type(NotificationType.EMAIL)
                        .status(NotificationStatus.SENT)
                        .build();

        when(repository.save(any(Notification.class)))
                .thenReturn(notification);

        NotificationResponse response =
                service.sendNotification(request);

        assertNotNull(response);
        assertEquals(
                NotificationStatus.SENT,
                response.getStatus()
        );
    }

    @Test
    void shouldFindById() {

        UUID id = UUID.randomUUID();

        Notification notification =
                Notification.builder()
                        .id(id)
                        .type(NotificationType.EMAIL)
                        .status(NotificationStatus.SENT)
                        .build();

        when(repository.findById(id))
                .thenReturn(Optional.of(notification));

        NotificationResponse response =
                service.getById(id);

        assertEquals(id, response.getId());
    }

    @Test
    void shouldThrowWhenNotFound() {

        UUID id = UUID.randomUUID();

        when(repository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(
                NotificationNotFoundException.class,
                () -> service.getById(id)
        );
    }

    @Test
    void shouldReturnNotificationsByUser() {

        UUID userId = UUID.randomUUID();

        Notification notification =
                Notification.builder()
                        .id(UUID.randomUUID())
                        .userId(userId)
                        .type(NotificationType.EMAIL)
                        .status(NotificationStatus.SENT)
                        .build();

        when(repository.findByUserId(userId))
                .thenReturn(List.of(notification));

        List<NotificationResponse> result =
                service.getByUserId(userId);

        assertEquals(1, result.size());
    }
}