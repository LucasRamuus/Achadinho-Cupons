package com.achadinhos_cupons.backend_api.application.usecases.admin;

import com.achadinhos_cupons.backend_api.domain.entities.Admin;
import com.achadinhos_cupons.backend_api.domain.gateways.AdminGateway;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListAdminsUseCase {

    private final AdminGateway repository;

    public ListAdminsUseCase(AdminGateway repository) {
        this.repository = repository;
    }

    public List<Admin> execute() {
        return repository.findAll();
    }
}

