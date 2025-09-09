package com.achadinhos_cupons.backend_api.application.usecases.product;

import com.achadinhos_cupons.backend_api.domain.gateways.ProductGateway;
import com.achadinhos_cupons.backend_api.domain.s3.S3Service;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeleteProductUseCase {

    private final ProductGateway productRepository;
    private final S3Service s3Service;

    public DeleteProductUseCase(ProductGateway productRepository, S3Service s3Service) {
        this.productRepository = productRepository;
        this.s3Service = s3Service;
    }

    public void execute(UUID id, String imageUrl) {
        boolean exists = productRepository.existsById(id);

        if (!exists) {
            throw new RuntimeException("Produto não encontrado com ID: " + id);
        }

        // Deleta a imagem do S3, se existir
        if (imageUrl != null && !imageUrl.isEmpty()) {
            String key = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
            s3Service.deleteFile(key);
        }

        // Deleta o produto do banco
        productRepository.deleteById(id);
    }
}
