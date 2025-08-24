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

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    /** Construtor protegido exigido pelo JPA */
    protected Admin() {}

    /** Construtor de domínio */
    public Admin(UUID id, String username, String passwordHash) {
        this.id = id;
        this.username = username;
        this.passwordHash = passwordHash;
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
}
