package com.achadinhos_cupons.backend_api.domain.entities;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(
        name = "admin",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_admin_username", columnNames = "username")
        }
)
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;

    /** Construtor protegido exigido pelo JPA */
    protected Admin() {}

    /** Construtor completo - para quando já tem ID */
    public Admin(UUID id, String username, String passwordHash) {
        this.id = id;
        this.username = username;
        this.passwordHash = passwordHash;
    }

    /** Construtor para criação inicial - CORRIGIDO */
    public Admin(String username, String passwordHash) {
        this.username = username;
        this.passwordHash = passwordHash;
        // ✅ REMOVIDO: this.id = UUID.randomUUID();
        // Deixe o @GeneratedValue gerar o ID automaticamente
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
        this.username = username;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
}