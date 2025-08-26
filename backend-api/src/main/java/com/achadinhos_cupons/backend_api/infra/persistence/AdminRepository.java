package com.achadinhos_cupons.backend_api.infra.persistence;

import com.achadinhos_cupons.backend_api.domain.entities.Admin;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AdminRepository {
    Admin save(Admin admin);
    Optional<Admin> findById(UUID id);
    Optional<Admin> findByUsername(String username);
    List<Admin> findAll();
    void deleteById(UUID id);
}