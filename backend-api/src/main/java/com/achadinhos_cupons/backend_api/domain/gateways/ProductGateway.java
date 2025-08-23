package com.achadinhos_cupons.backend_api.domain.gateways;

import com.achadinhos_cupons.backend_api.domain.entities.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductGateway {

    Product save(Product product);

    Optional<Product> findById(UUID id);

    List<Product> findAll();

    void deleteById(UUID id);

    Product update(Product product);

    boolean existsById(UUID id);
}
