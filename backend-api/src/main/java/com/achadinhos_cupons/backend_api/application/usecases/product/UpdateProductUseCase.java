package com.achadinhos_cupons.backend_api.application.usecases.product;

import com.achadinhos_cupons.backend_api.application.dtos.product.ProductRequestDTO;
import com.achadinhos_cupons.backend_api.application.dtos.product.ProductResponseDTO;
import com.achadinhos_cupons.backend_api.domain.entities.Product;
import com.achadinhos_cupons.backend_api.domain.gateways.ProductGateway;
import com.achadinhos_cupons.backend_api.domain.s3.S3Service;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

@Service
public class UpdateProductUseCase {

    private final ProductGateway productRepository;
    private final S3Service s3Service;

    public UpdateProductUseCase(ProductGateway productRepository, S3Service s3Service) {
        this.productRepository = productRepository;
        this.s3Service = s3Service;
    }

    public ProductResponseDTO execute(UUID id, ProductRequestDTO request) throws IOException {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado com ID: " + id));

        // 🔹 Atualiza imagem se houver novo arquivo
        if (request.getFile() != null && !request.getFile().isEmpty()) {
            String key = "products/" + product.getId();

            // Deleta qualquer arquivo antigo associado ao produto
            s3Service.deleteFilesWithPrefix(key);

            // Faz upload da nova imagem
            String imageUrl = s3Service.uploadFile(request.getFile(), key);
            product.setImage(imageUrl);
        }

        // 🔹 Atualiza demais campos
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setOldPrice(request.getOldPrice());
        product.setDescription(request.getDescription());
        product.setDiscountPercentage(request.getDiscountPercentage());
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
