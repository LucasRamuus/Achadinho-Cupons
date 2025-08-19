package com.achadinhos_cupons.backend_api.infra.gateways.persistence;

import com.achadinhos_cupons.backend_api.domain.entities.Admin;
import com.achadinhos_cupons.backend_api.domain.gateways.AdminGateway;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class AdminRepository implements AdminGateway {

    private final AdminJpaRepository jpa;

    public AdminRepository(AdminJpaRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public Admin save(Admin admin) {
        return jpa.save(admin);
    }

    @Override
    public Optional<Admin> findByUsername(String username) {
        return jpa.findByUsername(username);
    }

    @Override
    public List<Admin> findAll() {
        return List.of();
    }

    @Override
    public void deleteById(Long id) {

    }
}
