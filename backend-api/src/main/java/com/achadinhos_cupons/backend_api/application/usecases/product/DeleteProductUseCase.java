package com.achadinhos_cupons.backend_api.application.usecases.product;

import com.achadinhos_cupons.backend_api.domain.gateways.ProductGateway;

import java.util.UUID;

public class DeleteProductUseCase {

    private final ProductGateway productRepository;

    public DeleteProductUseCase(ProductGateway productRepository) {
        this.productRepository = productRepository;
    }

    public void execute(UUID id) {
        boolean exists = productRepository.existsById(id);

        if (!exists) {
            throw new RuntimeException("Produto não encontrado com ID: " + id);
        }

        productRepository.deleteById(id);
    }
}
