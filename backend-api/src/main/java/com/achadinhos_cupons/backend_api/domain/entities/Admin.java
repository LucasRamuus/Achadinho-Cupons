package com.achadinhos_cupons.backend_api.domain.entities;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;



public class Admin {

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private Long id;
    private String username;
    private String passwordHash;

    public Admin(Long id, String username, String rawPassword) {
        this.id = id;
        setUsername(username);
        setPassword(rawPassword);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        if (id != null && id < 0) {
            throw new IllegalArgumentException("ID must be positive.");
        }
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Username is required");
        }
        this.username = username.trim();
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    // Recebe a senha "crua" e grava o hash
    public void setPassword(String rawPassword) {
        if (rawPassword == null || rawPassword.length() < 6) {
            throw new IllegalArgumentException("Password must be at least 6 characters");
        }
        this.passwordHash = passwordEncoder.encode(rawPassword);
    }

    // Método para validar senha (compara senha crua com hash armazenado)
    public boolean checkPassword(String rawPassword) {
        return passwordEncoder.matches(rawPassword, this.passwordHash);
    }
}
