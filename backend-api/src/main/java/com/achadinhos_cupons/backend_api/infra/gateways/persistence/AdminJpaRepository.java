package com.achadinhos_cupons.backend_api.infra.gateways.persistence;

import com.achadinhos_cupons.backend_api.domain.entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminJpaRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findByUsername(String username);
}
