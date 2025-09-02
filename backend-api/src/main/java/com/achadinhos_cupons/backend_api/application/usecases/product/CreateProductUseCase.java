package com.achadinhos_cupons.backend_api.application.usecases.product;

import com.achadinhos_cupons.backend_api.application.dtos.product.ProductRequestDTO;
import com.achadinhos_cupons.backend_api.application.dtos.product.ProductResponseDTO;
import com.achadinhos_cupons.backend_api.domain.entities.Product;
import com.achadinhos_cupons.backend_api.domain.gateways.ProductGateway;
import com.achadinhos_cupons.backend_api.domain.s3.S3Service;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CreateProductUseCase {

    private final ProductGateway productRepository;
    private final S3Service s3Service;

    public CreateProductUseCase(ProductGateway productRepository, S3Service s3Service) {
        this.productRepository = productRepository;
        this.s3Service = s3Service;
    }

    public ProductResponseDTO execute(ProductRequestDTO request) throws IOException {
        Product product = new Product(
                null,
                request.getName(),
                request.getPrice(),
                request.getOldPrice(),
                request.getDescription(),
                request.getDiscountPercentage(),
                null,
                request.getAffiliateLink()
        );

        Product savedProduct = productRepository.save(product);

        if (request.getFile() != null && !request.getFile().isEmpty()) {
            String extension = getFileExtension(request.getFile().getOriginalFilename());
            String key = savedProduct.getId().toString() + extension;

            String imageUrl = s3Service.uploadFile(request.getFile(), key);
            savedProduct.setImage(imageUrl);
            savedProduct = productRepository.update(savedProduct);
        }

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

    private String getFileExtension(String filename) {
        return filename.substring(filename.lastIndexOf('.'));
    }
}
