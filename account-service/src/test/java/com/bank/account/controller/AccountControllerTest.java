package com.bank.account.controller;

import com.bank.account.dto.AccountResponse;
import com.bank.account.dto.CreateAccountRequest;
import com.bank.account.enums.AccountStatus;
import com.bank.account.service.AccountService;
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

@WebMvcTest(AccountController.class)
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateAccount() throws Exception {

        UUID userId = UUID.randomUUID();

        CreateAccountRequest request =
                CreateAccountRequest.builder()
                        .userId(userId)
                        .build();

        AccountResponse response =
                AccountResponse.builder()
                        .id(UUID.randomUUID())
                        .userId(userId)
                        .accountNumber("000001")
                        .agency("0001")
                        .balance(BigDecimal.ZERO)
                        .status(AccountStatus.ACTIVE)
                        .build();

        when(accountService.createAccount(any()))
                .thenReturn(response);

        mockMvc.perform(post("/api/v1/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                objectMapper.writeValueAsString(request)
                        ))
                .andExpect(status().isOk());
    }
}