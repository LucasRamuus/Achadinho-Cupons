package com.achadinhos_cupons.backend_api.infra.persistence;

import com.achadinhos_cupons.backend_api.domain.entities.Admin;
import com.achadinhos_cupons.backend_api.domain.gateways.AdminGateway;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class AdminRepositoryImpl implements AdminGateway {

    private final JpaAdminRepository jpaRepository;

    public AdminRepositoryImpl(JpaAdminRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Admin save(Admin admin) {
        return jpaRepository.save(admin);
    }

    @Override
    public Optional<Admin> findById(UUID id) {
        return jpaRepository.findById(id);
    }

    @Override
    public Optional<Admin> findByUsername(String username) {
        return jpaRepository.findByUsername(username);
    }

    @Override
    public List<Admin> findAll() {
        return jpaRepository.findAll();
    }

    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public boolean existsById(UUID id) {
        return jpaRepository.existsById(id);
    }

}


