package com.bank.auth.repository;

import com.bank.auth.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldFindUserByEmail() {

        User user = User.builder()
                .name("Gabriel")
                .email("gabriel@email.com")
                .cpf("12345678900")
                .password("123456")
                .birthDate(LocalDate.of(2000, 1, 1))
                .build();

        userRepository.save(user);

        var result =
                userRepository.findByEmail("gabriel@email.com");

        assertTrue(result.isPresent());
    }

    @Test
    void shouldCheckIfEmailExists() {

        User user = User.builder()
                .name("Gabriel")
                .email("gabriel@email.com")
                .cpf("12345678900")
                .password("123456")
                .birthDate(LocalDate.of(2000, 1, 1))
                .build();

        userRepository.save(user);

        boolean exists =
                userRepository.existsByEmail(
                        "gabriel@email.com"
                );

        assertTrue(exists);
    }

    @Test
    void shouldCheckIfCpfExists() {

        User user = User.builder()
                .name("Gabriel")
                .email("gabriel@email.com")
                .cpf("12345678900")
                .password("123456")
                .birthDate(LocalDate.of(2000, 1, 1))
                .build();

        userRepository.save(user);

        boolean exists =
                userRepository.existsByCpf(
                        "12345678900"
                );

        assertTrue(exists);
    }
}