package com.bank.notification.controller;

import com.bank.notification.dto.NotificationRequest;
import com.bank.notification.dto.NotificationResponse;
import com.bank.notification.enums.NotificationStatus;
import com.bank.notification.enums.NotificationType;
import com.bank.notification.service.NotificationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(NotificationController.class)
class NotificationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NotificationService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldSendNotification() throws Exception {

        NotificationRequest request =
                NotificationRequest.builder()
                        .userId(UUID.randomUUID())
                        .destination("user@email.com")
                        .message("Test")
                        .type(NotificationType.EMAIL)
                        .build();

        NotificationResponse response =
                NotificationResponse.builder()
                        .id(UUID.randomUUID())
                        .status(NotificationStatus.SENT)
                        .build();

        when(service.sendNotification(any()))
                .thenReturn(response);

        mockMvc.perform(
                        post("/api/v1/notifications")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(
                                                request
                                        )
                                )
                )
                .andExpect(status().isOk());
    }
}