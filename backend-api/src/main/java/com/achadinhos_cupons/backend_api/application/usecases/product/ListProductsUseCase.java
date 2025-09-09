package com.achadinhos_cupons.backend_api.application.usecases.product;


import com.achadinhos_cupons.backend_api.application.dtos.product.ProductResponseDTO;
import com.achadinhos_cupons.backend_api.domain.entities.Product;
import com.achadinhos_cupons.backend_api.domain.gateways.ProductGateway;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class ListProductsUseCase {

    private final ProductGateway productRepository;

    public ListProductsUseCase(ProductGateway productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductResponseDTO> execute() {
        List<Product> products = productRepository.findAll();

        return products.stream()
                .map(product -> new ProductResponseDTO(
                        product.getId(),
                        product.getName(),
                        product.getPrice(),
                        product.getOldPrice(),
                        product.getDiscountPercentage(),
                        product.getImage(),
                        product.getAffiliateLink(),
                        product.getFeatured()
                ))
                .collect(Collectors.toList());
    }
}