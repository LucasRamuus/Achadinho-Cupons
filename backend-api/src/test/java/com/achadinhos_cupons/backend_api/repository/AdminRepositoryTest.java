package com.achadinhos_cupons.backend_api.repository;

import com.achadinhos_cupons.backend_api.domain.entities.Admin;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class AdminRepositoryTest {

    @Autowired
    private AdminJpaRepository repo;

    @Test
    void shouldSaveAndFindByUsername() {
        Admin admin = new Admin(null, "admin", "secret123");
        repo.save(admin);

        Optional<Admin> found = repo.findByUsername("admin");
        assertThat(found).isPresent();
        assertThat(found.get().checkPassword("secret123")).isTrue();
    }
}
