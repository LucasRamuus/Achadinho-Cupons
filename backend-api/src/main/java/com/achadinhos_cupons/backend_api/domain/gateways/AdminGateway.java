package com.achadinhos_cupons.backend_api.domain.gateways;

import com.achadinhos_cupons.backend_api.domain.entities.Admin;
import com.achadinhos_cupons.backend_api.domain.entities.Product;

import java.util.List;
import java.util.Optional;

public interface AdminGateway {
    Admin save(Admin admin);

    Optional<Admin> findByUsername(String username);

    List<Admin> findAll();

    void deleteById(Long id);
}
