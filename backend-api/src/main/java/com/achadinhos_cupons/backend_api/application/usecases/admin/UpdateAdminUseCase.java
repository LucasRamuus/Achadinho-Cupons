package com.achadinhos_cupons.backend_api.application.usecases.admin;

import com.achadinhos_cupons.backend_api.domain.entities.Admin;
import com.achadinhos_cupons.backend_api.domain.gateways.AdminGateway;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;


@Service
public class UpdateAdminUseCase {

    private final AdminGateway repository;

    public UpdateAdminUseCase(AdminGateway repository) {
        this.repository = repository;
    }

    public Optional<Admin> execute(UUID id, Admin updatedAdmin) {
        return repository.findById(id).map(existing -> {
            existing.setUsername(updatedAdmin.getUsername());
            existing.setPasswordHash(updatedAdmin.getPasswordHash());
            return repository.save(existing);
        });
    }
}
