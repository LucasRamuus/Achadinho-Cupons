package com.achadinhos_cupons.backend_api.infra.controllers;

import com.achadinhos_cupons.backend_api.application.usecases.admin.CreateAdminUseCase;
import com.achadinhos_cupons.backend_api.application.usecases.admin.DeleteAdminUseCase;
import com.achadinhos_cupons.backend_api.application.usecases.admin.GetAdminByIdUseCase;
import com.achadinhos_cupons.backend_api.application.usecases.admin.ListAdminsUseCase;
import com.achadinhos_cupons.backend_api.domain.entities.Admin;
import com.achadinhos_cupons.backend_api.application.dtos.admin.AdminRequestDTO;
import com.achadinhos_cupons.backend_api.application.dtos.admin.AdminResponseDTO;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admins")
public class AdminController {

    private final CreateAdminUseCase createAdminUseCase;
    private final GetAdminByIdUseCase getAdminByIdUseCase;
    private final ListAdminsUseCase listAdminsUseCase;
    private final DeleteAdminUseCase deleteAdminUseCase; //

    public AdminController(CreateAdminUseCase createAdminUseCase,
                           GetAdminByIdUseCase getAdminByIdUseCase,
                           ListAdminsUseCase listAdminsUseCase,
                           DeleteAdminUseCase deleteAdminUseCase, DeleteAdminUseCase deleteAdminUseCase1) {
        this.createAdminUseCase = createAdminUseCase;
        this.getAdminByIdUseCase = getAdminByIdUseCase;
        this.listAdminsUseCase = listAdminsUseCase;
        this.deleteAdminUseCase = deleteAdminUseCase1;

    }

    @GetMapping("/{id}")
    public ResponseEntity<AdminResponseDTO> getById(@PathVariable UUID id) {
        return getAdminByIdUseCase.execute(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<AdminResponseDTO>> listAll() {
        List<AdminResponseDTO> admins = listAdminsUseCase.execute()
                .stream()
                .map(AdminResponseDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(admins);
    }

    
}