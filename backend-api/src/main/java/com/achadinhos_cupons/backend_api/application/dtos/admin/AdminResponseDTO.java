package com.achadinhos_cupons.backend_api.application.dtos.admin;

import com.achadinhos_cupons.backend_api.domain.entities.Admin;

import java.util.UUID;

public class AdminResponseDTO {
    private UUID id;
    private String username;

    public AdminResponseDTO() {}

    public AdminResponseDTO(UUID id, String username) {
        this.id = id;
        this.username = username;
    }

    public AdminResponseDTO(Admin savedAdmin) {
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}