package com.achadinhos_cupons.backend_api.domain.entities;

import jakarta.persistence.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

@Entity
@Table(
        name = "admin",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_admin_username", columnNames = "username")
        }
)
public class Admin {

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    /** Construtor protegido exigido pelo JPA */
    protected Admin() {}

    /** Construtor de domínio (gera o hash automaticamente) */
    public Admin(UUID id, String username, String rawPassword) {
        this.id = id;
        setUsername(username);
        setPassword(rawPassword);
    }

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setUsername(String username) {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("O nome de usuário não pode ser vazio");
        }
        this.username = username;
    }

    /**
     * Recebe a senha em texto puro e guarda o hash.
     */
    public void setPassword(String rawPassword) {
        if (rawPassword == null || rawPassword.length() < 6) {
            throw new IllegalArgumentException("A senha deve ter pelo menos 6 caracteres");
        }
        this.passwordHash = passwordEncoder.encode(rawPassword);
    }

    /**
     * Verifica se a senha fornecida bate com o hash armazenado.
     */
    public boolean checkPassword(String rawPassword) {
        return passwordEncoder.matches(rawPassword, this.passwordHash);
    }
}
