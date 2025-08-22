package com.achadinhos_cupons.backend_api.infra.persistence;

import com.achadinhos_cupons.backend_api.domain.entities.Product;
import com.achadinhos_cupons.backend_api.domain.gateways.ProductGateway;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ProductRepositoryImpl implements ProductGateway {

    private final ProductJpaRepository jpaRepository;

    public ProductRepositoryImpl(ProductJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Product save(Product product) {
        return jpaRepository.save(product);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return jpaRepository.findById(id);
    }

    @Override
    public List<Product> findAll() {
        return jpaRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public Product update(Product product) {
        return null;
    }

    @Override
    public boolean existsById(Long id) {
        return false;
    }
}
