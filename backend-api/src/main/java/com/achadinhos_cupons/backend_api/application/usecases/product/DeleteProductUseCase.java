package com.achadinhos_cupons.backend_api.application.usecases.product;

import com.achadinhos_cupons.backend_api.domain.gateways.ProductGateway;

public class DeleteProductUseCase {

    private final ProductGateway productRepository;

    public DeleteProductUseCase(ProductGateway productRepository) {
        this.productRepository = productRepository;
    }

    public void execute(Long id) {
        boolean exists = productRepository.existsById(id);

        if (!exists) {
            throw new RuntimeException("Produto não encontrado com ID: " + id);
        }

        productRepository.deleteById(id);
    }
}
