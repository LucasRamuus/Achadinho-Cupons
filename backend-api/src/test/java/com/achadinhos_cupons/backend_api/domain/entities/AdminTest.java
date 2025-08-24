package com.achadinhos_cupons.backend_api.domain.entities;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AdminTest {

    @Test
    void deveCriarAdminComSucesso() {
        UUID id = UUID.randomUUID();
        String username = "adminUser";
        String passwordHash = "$2a$10$hashExemplo1234567890"; // simulação de hash bcrypt

        Admin admin = new Admin(id, username, passwordHash);

        assertNotNull(admin);
        assertEquals(id, admin.getId());
        assertEquals(username, admin.getUsername());
        assertEquals(passwordHash, admin.getPasswordHash());
    }

    @Test
    void devePermitirUsernameEPasswordHashNaoNulos() {
        UUID id = UUID.randomUUID();
        String username = "userTest";
        String passwordHash = "hash123";

        Admin admin = new Admin(id, username, passwordHash);

        assertNotNull(admin.getUsername());
        assertNotNull(admin.getPasswordHash());
    }

    @Test
    void deveFalharSeCriarAdminComUsernameNulo() {
        UUID id = UUID.randomUUID();
        String passwordHash = "hash123";

        assertThrows(NullPointerException.class, () -> {
            new Admin(id, null, passwordHash);
        });
    }

    @Test
    void deveFalharSeCriarAdminComPasswordHashNulo() {
        UUID id = UUID.randomUUID();
        String username = "userTest";

        assertThrows(NullPointerException.class, () -> {
            new Admin(id, username, null);
        });
    }
}
