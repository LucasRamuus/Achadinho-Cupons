package com.achadinhos_cupons.backend_api.infra.persistence;

import com.achadinhos_cupons.backend_api.domain.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductJpaRepository extends JpaRepository<Product, UUID> {
    void deleteById(UUID id);

    Optional<Product> findById(UUID id);
    // Aqui você pode criar queries customizadas se precisar, ex:
    // Optional<Product> findByName(String name);
}
