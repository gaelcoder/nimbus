package com.bank.auth.repository;

import com.bank.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    // Buscar por e-mail (usado no login)
    Optional<User> findByEmail(String email);

    // Buscar por CPF (usado para validar se o usuário já existe no cadastro)
    Optional<User> findByCpf(String cpf);

    // Verificar se já existe antes de salvar
    Boolean existsByEmail(String email);
    Boolean existsByCpf(String cpf);
}