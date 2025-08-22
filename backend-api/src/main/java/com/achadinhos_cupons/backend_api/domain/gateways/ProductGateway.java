package com.achadinhos_cupons.backend_api.domain.gateways;

import com.achadinhos_cupons.backend_api.domain.entities.Product;

import java.util.List;
import java.util.Optional;

public interface ProductGateway {

    Product save(Product product);

    Optional<Product> findById(Long id);

    List<Product> findAll();

    void deleteById(Long id);

    Product update(Product product);

    boolean existsById(Long id);
}
