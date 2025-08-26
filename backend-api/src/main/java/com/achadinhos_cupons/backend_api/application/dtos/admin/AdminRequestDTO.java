package com.achadinhos_cupons.backend_api.application.dtos.admin;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AdminRequestDTO {
    private String username;

    @JsonProperty("password_hash")
    private String password_hash;

    public AdminRequestDTO() {}

    public AdminRequestDTO(String username, String password_hash) {
        this.username = username;
        this.password_hash = password_hash;
    }

    // Getters e Setters CORRETOS
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword_hash() {
        return password_hash;
    }

    public void setPassword_hash(String password_hash) {
        this.password_hash = password_hash;
    }
}