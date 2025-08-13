package com.achadinhos_cupons.backend_api.infra.gateways;

import com.achadinhos_cupons.backend_api.domain.entities.Product;
import com.achadinhos_cupons.backend_api.domain.gateways.ProductGateway;
import com.achadinhos_cupons.backend_api.infra.gateways.persistence.ProductRepository;
import org.springframework.stereotype.Component;


import java.util.List;
import java.util.Optional;

@Component
public class ProductRepositoryGateway implements ProductGateway {

    private final ProductRepository repository;

    public ProductRepositoryGateway(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public Product save(Product product) {
        return repository.save(product);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Product> findAll() {
        return repository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
