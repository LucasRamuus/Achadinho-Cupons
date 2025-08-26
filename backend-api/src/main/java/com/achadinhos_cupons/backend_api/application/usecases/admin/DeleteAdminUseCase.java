package com.achadinhos_cupons.backend_api.application.usecases.admin;
import com.achadinhos_cupons.backend_api.domain.gateways.AdminGateway;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class DeleteAdminUseCase {

    private final AdminGateway repository;

    public DeleteAdminUseCase(AdminGateway repository) {
        this.repository = repository;
    }

    public void execute(UUID id) {
        repository.deleteById(id);
    }

    public void deleteById(UUID id) {
    }

    public boolean existsById(UUID id) {
        return repository.existsById(id); // ✅ Implementa corretamente
    }

}