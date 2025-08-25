package com.achadinhos_cupons.backend_api.application.usecases.product;

import com.achadinhos_cupons.backend_api.application.dtos.product.ProductRequestDTO;
import com.achadinhos_cupons.backend_api.application.dtos.product.ProductResponseDTO;
import com.achadinhos_cupons.backend_api.domain.entities.Product;
import com.achadinhos_cupons.backend_api.domain.gateways.ProductGateway;
import org.springframework.stereotype.Service;

@Service
public class CreateProductUseCase {

    private final ProductGateway productRepository;

    public CreateProductUseCase(ProductGateway productRepository) {
        this.productRepository = productRepository;
    }

    public ProductResponseDTO execute(ProductRequestDTO request) {
        // Criar entidade a partir do DTO
        Product product = new Product(
                null,
                request.getName(),
                request.getPrice(),
                request.getOldPrice(),
                request.getDescription(),
                request.getDiscountPercentage(),
                request.getImage(),
                request.getAffiliateLink()
        );

        // Persistir no repositório
        Product savedProduct = productRepository.save(product);

        // Retornar Response DTO
        return new ProductResponseDTO(
                savedProduct.getId(),
                savedProduct.getName(),
                savedProduct.getPrice(),
                savedProduct.getOldPrice(),
                savedProduct.getDescription(),
                savedProduct.getDiscountPercentage(),
                savedProduct.getImage(),
                savedProduct.getAffiliateLink()
        );
    }
}