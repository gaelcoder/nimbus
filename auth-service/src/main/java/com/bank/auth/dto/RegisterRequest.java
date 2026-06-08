package com.bank.auth.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String cpf;

    @NotBlank
    @Size(min = 8)
    private String password;

    @NotNull
    private LocalDate birthDate;
}