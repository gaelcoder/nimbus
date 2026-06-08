package com.bank.auth.service.impl;

import com.bank.auth.dto.AuthResponse;
import com.bank.auth.dto.LoginRequest;
import com.bank.auth.dto.RegisterRequest;
import com.bank.auth.entity.User;
import com.bank.auth.exception.ResourceNotFoundException;
import com.bank.auth.exception.UserAlreadyExistsException;
import com.bank.auth.repository.UserRepository;
import com.bank.auth.security.JwtService;
import com.bank.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponse register(RegisterRequest request) {

        String cpf = request.getCpf().replaceAll("\\D", "");

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException(
                    "Email already registered"
            );
        }

        if (userRepository.existsByCpf(cpf)) {
            throw new RuntimeException("CPF already registered");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .cpf(cpf)
                .password(passwordEncoder.encode(request.getPassword()))
                .birthDate(request.getBirthDate())
                .active(true)
                .build();

        userRepository.save(user);

        String jwtToken = jwtService.generateToken(user);

        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        // 1. O Spring Security tenta autenticar com email e senha.
        // Se a senha estiver errada, ele joga uma exceção sozinho aqui.
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // 2. Se passou da linha de cima, a senha tá certa! Pega o usuário do banco.
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"
                        ));

        // 3. Gera o token e devolve
        var jwtToken = jwtService.generateToken(user);
        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }
}