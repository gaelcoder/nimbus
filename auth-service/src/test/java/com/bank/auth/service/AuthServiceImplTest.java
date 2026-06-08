package com.bank.auth.service;

import com.bank.auth.dto.AuthResponse;
import com.bank.auth.dto.LoginRequest;
import com.bank.auth.dto.RegisterRequest;
import com.bank.auth.entity.User;
import com.bank.auth.exception.ResourceNotFoundException;
import com.bank.auth.exception.UserAlreadyExistsException;
import com.bank.auth.repository.UserRepository;
import com.bank.auth.security.JwtService;
import com.bank.auth.service.impl.AuthServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthServiceImpl authService;

    @Test
    void shouldRegisterUserSuccessfully() {

        RegisterRequest request = RegisterRequest.builder()
                .name("Gabriel")
                .email("gabriel@email.com")
                .cpf("12345678900")
                .password("12345678")
                .birthDate(LocalDate.of(2000, 1, 1))
                .build();

        when(userRepository.existsByEmail(any()))
                .thenReturn(false);

        when(userRepository.existsByCpf(any()))
                .thenReturn(false);

        when(passwordEncoder.encode(any()))
                .thenReturn("encoded-password");

        when(jwtService.generateToken(any()))
                .thenReturn("jwt-token");

        AuthResponse response =
                authService.register(request);

        assertNotNull(response);
        assertEquals("jwt-token", response.getToken());

        verify(userRepository, times(1))
                .save(any(User.class));
    }

    @Test
    void shouldThrowExceptionWhenEmailAlreadyExists() {

        RegisterRequest request = RegisterRequest.builder()
                .email("gabriel@email.com")
                .build();

        when(userRepository.existsByEmail(any()))
                .thenReturn(true);

        assertThrows(
                UserAlreadyExistsException.class,
                () -> authService.register(request)
        );

        verify(userRepository, never())
                .save(any());
    }

    @Test
    void shouldThrowExceptionWhenCpfAlreadyExists() {

        RegisterRequest request = RegisterRequest.builder()
                .cpf("12345678900")
                .build();

        when(userRepository.existsByEmail(any()))
                .thenReturn(false);

        when(userRepository.existsByCpf(any()))
                .thenReturn(true);

        assertThrows(
                UserAlreadyExistsException.class,
                () -> authService.register(request)
        );

        verify(userRepository, never())
                .save(any());
    }

    @Test
    void shouldLoginSuccessfully() {

        LoginRequest request = LoginRequest.builder()
                .email("gabriel@email.com")
                .password("12345678")
                .build();

        User user = User.builder()
                .email("gabriel@email.com")
                .password("encoded")
                .build();

        when(userRepository.findByEmail(any()))
                .thenReturn(Optional.of(user));

        when(jwtService.generateToken(any()))
                .thenReturn("jwt-token");

        AuthResponse response =
                authService.login(request);

        assertNotNull(response);
        assertEquals("jwt-token", response.getToken());

        verify(authenticationManager)
                .authenticate(any());
    }

    @Test
    void shouldThrowExceptionWhenUserDoesNotExist() {

        LoginRequest request = LoginRequest.builder()
                .email("notfound@email.com")
                .password("12345678")
                .build();

        when(userRepository.findByEmail(any()))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> authService.login(request)
        );
    }
}