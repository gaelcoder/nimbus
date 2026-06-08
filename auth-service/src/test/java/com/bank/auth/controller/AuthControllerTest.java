package com.bank.auth.controller;

import com.bank.auth.dto.AuthResponse;
import com.bank.auth.service.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @Test
    void shouldRegisterUser() throws Exception {

        when(authService.register(any()))
                .thenReturn(
                        AuthResponse.builder()
                                .token("jwt-token")
                                .build()
                );

        String json = """
                {
                  "name":"Gabriel",
                  "email":"gabriel@email.com",
                  "cpf":"12345678900",
                  "password":"12345678",
                  "birthDate":"2000-01-01"
                }
                """;

        mockMvc.perform(
                        post("/api/v1/auth/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token")
                        .value("jwt-token"));
    }

    @Test
    void shouldLoginUser() throws Exception {

        when(authService.login(any()))
                .thenReturn(
                        AuthResponse.builder()
                                .token("jwt-token")
                                .build()
                );

        String json = """
                {
                  "email":"gabriel@email.com",
                  "password":"12345678"
                }
                """;

        mockMvc.perform(
                        post("/api/v1/auth/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token")
                        .value("jwt-token"));
    }
}