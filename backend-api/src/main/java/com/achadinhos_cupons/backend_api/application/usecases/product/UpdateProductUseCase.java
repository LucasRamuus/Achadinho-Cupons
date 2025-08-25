package com.achadinhos_cupons.backend_api.application.usecases.product;

import com.achadinhos_cupons.backend_api.application.dtos.product.ProductRequestDTO;
import com.achadinhos_cupons.backend_api.application.dtos.product.ProductResponseDTO;
import com.achadinhos_cupons.backend_api.domain.entities.Product;
import com.achadinhos_cupons.backend_api.domain.gateways.ProductGateway;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UpdateProductUseCase {

    private final ProductGateway productRepository;

    public UpdateProductUseCase(ProductGateway productRepository) {
        this.productRepository = productRepository;
    }

    public ProductResponseDTO execute(UUID id, ProductRequestDTO request) {
        Optional<Product> productOpt = productRepository.findById(id);

        if (productOpt.isEmpty()) {
            throw new RuntimeException("Produto não encontrado com ID: " + id);
        }

        Product product = productOpt.get();

        // atualizar os campos
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setOldPrice(request.getOldPrice());
        product.setDescription(request.getDescription());
        product.setDiscountPercentage(request.getDiscountPercentage());
        product.setImage(request.getImage());
        product.setAffiliateLink(request.getAffiliateLink());

        Product updatedProduct = productRepository.update(product);

        return new ProductResponseDTO(
                updatedProduct.getId(),
                updatedProduct.getName(),
                updatedProduct.getPrice(),
                updatedProduct.getOldPrice(),
                updatedProduct.getDescription(),
                updatedProduct.getDiscountPercentage(),
                updatedProduct.getImage(),
                updatedProduct.getAffiliateLink()
        );
    }
}

