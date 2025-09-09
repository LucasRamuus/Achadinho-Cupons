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
            // Se já tinha imagem antiga → deleta
            if (product.getImage() != null && !product.getImage().isEmpty()) {
                String oldKey = extractKeyFromUrl(product.getImage());
                s3Service.deleteFile("products/" + oldKey);
            }

            // Gera nova chave com extensão
            String extension = getFileExtension(request.getFile().getOriginalFilename());
            String key =  product.getId() + extension;

            // Faz upload
            String imageUrl = s3Service.uploadFile(request.getFile(), key);
            product.setImage(imageUrl);
        }

        // 🔹 Atualiza demais campos
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setOldPrice(request.getOldPrice());
        product.setDiscountPercentage(request.getDiscountPercentage());
        product.setAffiliateLink(request.getAffiliateLink());
        product.setFeatured(request.getFeatured());

        Product updatedProduct = productRepository.update(product);

        return new ProductResponseDTO(
                updatedProduct.getId(),
                updatedProduct.getName(),
                updatedProduct.getPrice(),
                updatedProduct.getOldPrice(),
                updatedProduct.getDiscountPercentage(),
                updatedProduct.getImage(),
                updatedProduct.getAffiliateLink(),
                updatedProduct.getFeatured()
        );
    }

    private String getFileExtension(String filename) {
        return filename.substring(filename.lastIndexOf('.'));
    }

    private String extractKeyFromUrl(String url) {
        return url.substring(url.lastIndexOf('/') + 1);
    }
}
