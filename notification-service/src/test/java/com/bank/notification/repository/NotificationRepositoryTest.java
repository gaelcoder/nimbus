package com.bank.notification.repository;

import com.bank.notification.entity.Notification;
import com.bank.notification.enums.NotificationStatus;
import com.bank.notification.enums.NotificationType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class NotificationRepositoryTest {

    @Autowired
    private NotificationRepository repository;

    @Test
    void shouldFindByUserId() {

        UUID userId = UUID.randomUUID();

        Notification notification =
                Notification.builder()
                        .userId(userId)
                        .destination("user@email.com")
                        .message("Test")
                        .type(NotificationType.EMAIL)
                        .status(NotificationStatus.SENT)
                        .build();

        repository.save(notification);

        List<Notification> result =
                repository.findByUserId(userId);

        assertFalse(result.isEmpty());
    }
}