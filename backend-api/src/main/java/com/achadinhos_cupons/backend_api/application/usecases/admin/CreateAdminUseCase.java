package com.achadinhos_cupons.backend_api.application.usecases.admin;

import com.achadinhos_cupons.backend_api.application.dtos.admin.AdminRequestDTO;
import com.achadinhos_cupons.backend_api.application.dtos.admin.AdminResponseDTO;
import com.achadinhos_cupons.backend_api.domain.entities.Admin;
import com.achadinhos_cupons.backend_api.domain.gateways.AdminGateway;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateAdminUseCase {

    private final AdminGateway adminRepository;
    private final PasswordEncoder passwordEncoder;


    public CreateAdminUseCase(AdminGateway adminRepository, PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public AdminResponseDTO createAdmin(AdminRequestDTO requestDTO) {
        // 1. Validação
        if (requestDTO.getPassword_hash() == null || requestDTO.getPassword_hash().trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }

        if (requestDTO.getUsername() == null || requestDTO.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }

        // 3. Criptografa a senha
        String hashedPassword = passwordEncoder.encode(requestDTO.getPassword_hash());

        // 4. Cria a entidade (SEM passar ID - let JPA generate it)
        Admin admin = new Admin(requestDTO.getUsername(), hashedPassword);

        // 5. Salva no banco
        Admin savedAdmin = adminRepository.save(admin);

        // 6. Retorna DTO de resposta
        return new AdminResponseDTO(savedAdmin.getId(), savedAdmin.getUsername());
    }


}