package com.achadinhos_cupons.backend_api.application.usecases.product;

import com.achadinhos_cupons.backend_api.application.dtos.product.ProductResponseDTO;
import com.achadinhos_cupons.backend_api.domain.entities.Product;
import com.achadinhos_cupons.backend_api.domain.gateways.ProductGateway;

import java.util.Optional;
import java.util.UUID;

public class GetProductByIdUseCase {

    private final ProductGateway productRepository;

    public GetProductByIdUseCase(ProductGateway productRepository) {
        this.productRepository = productRepository;
    }

    public ProductResponseDTO execute(UUID id) {
        Optional<Product> productOpt = productRepository.findById(id);

        if (productOpt.isEmpty()) {
            throw new RuntimeException("Produto não encontrado com ID: " + id);
        }

        Product product = productOpt.get();

        return new ProductResponseDTO(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getOldPrice(),
                product.getDescription(),
                product.getDiscountPercentage(),
                product.getImage(),
                product.getAffiliateLink()
        );
    }
}