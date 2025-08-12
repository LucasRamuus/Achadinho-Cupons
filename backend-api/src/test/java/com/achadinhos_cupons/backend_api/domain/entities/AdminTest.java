package com.achadinhos_cupons.backend_api.domain.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AdminTest {

    private Admin admin;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @BeforeEach
    void setUp() {
        admin = new Admin(1L, "admin_test", "senha123");
    }

    // Teste de criação e atributos básicos
    @Test
    void whenCreatingAdmin_thenFieldsAreSetCorrectly() {
        assertThat(admin.getId()).isEqualTo(1L);
        assertThat(admin.getUsername()).isEqualTo("admin_test");
        assertThat(admin.getPasswordHash()).isNotEqualTo("senha123"); // Deve estar hasheado
    }

    // Teste de codificação de senha
    @Test
    void whenSettingPassword_thenPasswordIsEncoded() {
        String rawPassword = "outrasenha456";
        admin.setPassword(rawPassword);

        assertThat(admin.getPasswordHash())
                .isNotEqualTo(rawPassword)
                .matches(encoded -> passwordEncoder.matches(rawPassword, encoded));
    }

    // Teste de verificação de senha correta
    @Test
    void whenCheckingCorrectPassword_thenReturnsTrue() {
        assertThat(admin.checkPassword("senha123")).isTrue();
    }

    // Teste de verificação de senha incorreta
    @Test
    void whenCheckingWrongPassword_thenReturnsFalse() {
        assertThat(admin.checkPassword("senha_errada")).isFalse();
    }

    // Teste de validação de senha curta
    @Test
    void whenSettingShortPassword_thenThrowsException() {
        assertThatThrownBy(() -> admin.setPassword("abc"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Password must be at least 6 characters");
    }

    // Teste de validação de username vazio
    @Test
    void whenSettingBlankUsername_thenThrowsException() {
        assertThatThrownBy(() -> admin.setUsername("   "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Username is required");
    }
}