package com.achadinhos_cupons.backend_api.application.usecases.admin;

import com.achadinhos_cupons.backend_api.application.dtos.admin.AdminResponseDTO;
import com.achadinhos_cupons.backend_api.domain.gateways.AdminGateway;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class GetAdminByIdUseCase {

    private final AdminGateway adminRepository;

    public GetAdminByIdUseCase(AdminGateway adminRepository) {
        this.adminRepository = adminRepository;
    }

    public Optional<AdminResponseDTO> execute(UUID id) {
        return adminRepository.findById(id)
                .map(admin -> new AdminResponseDTO(admin.getId(), admin.getUsername()));
    }

}
