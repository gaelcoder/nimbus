package com.bank.transaction.controller;

import com.bank.transaction.dto.CreateTransactionRequest;
import com.bank.transaction.dto.TransactionResponse;
import com.bank.transaction.enums.TransactionStatus;
import com.bank.transaction.enums.TransactionType;
import com.bank.transaction.service.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransactionController.class)
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateTransaction() throws Exception {

        UUID source = UUID.randomUUID();
        UUID destination = UUID.randomUUID();

        CreateTransactionRequest request =
                CreateTransactionRequest.builder()
                        .sourceAccountId(source)
                        .destinationAccountId(destination)
                        .amount(BigDecimal.TEN)
                        .build();

        TransactionResponse response =
                TransactionResponse.builder()
                        .id(UUID.randomUUID())
                        .sourceAccountId(source)
                        .destinationAccountId(destination)
                        .amount(BigDecimal.TEN)
                        .type(TransactionType.TRANSFER)
                        .status(TransactionStatus.COMPLETED)
                        .build();

        when(transactionService.createTransaction(any()))
                .thenReturn(response);

        mockMvc.perform(
                        post("/api/v1/transactions")
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