package com.achadinhos_cupons.backend_api.infra.gateways.persistence;

import com.achadinhos_cupons.backend_api.domain.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}