package com.achadinhos_cupons.backend_api.infra.controllers;

import com.achadinhos_cupons.backend_api.application.dtos.product.ProductRequestDTO;
import com.achadinhos_cupons.backend_api.application.dtos.product.ProductResponseDTO;
import com.achadinhos_cupons.backend_api.application.usecases.product.*;
import com.achadinhos_cupons.backend_api.domain.s3.S3Service;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final CreateProductUseCase createProductUseCase;
    private final GetProductByIdUseCase getProductByIdUseCase;
    private final ListProductsUseCase listProductsUseCase;
    private final UpdateProductUseCase updateProductUseCase;
    private final DeleteProductUseCase deleteProductUseCase;
    private final S3Service s3Service;

    public ProductController(CreateProductUseCase createProductUseCase,
                             GetProductByIdUseCase getProductByIdUseCase,
                             ListProductsUseCase listProductsUseCase,
                             UpdateProductUseCase updateProductUseCase,
                             DeleteProductUseCase deleteProductUseCase,
                             S3Service s3Service) {
        this.createProductUseCase = createProductUseCase;
        this.getProductByIdUseCase = getProductByIdUseCase;
        this.listProductsUseCase = listProductsUseCase;
        this.updateProductUseCase = updateProductUseCase;
        this.deleteProductUseCase = deleteProductUseCase;
        this.s3Service = s3Service;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductResponseDTO> create(
            @RequestParam("name") String name,
            @RequestParam("price") Double price,
            @RequestParam(value = "oldPrice", required = false) Double oldPrice,
            @RequestParam("description") String description,
            @RequestParam(value = "discountPercentage", required = false) Double discountPercentage,
            @RequestParam("affiliateLink") String affiliateLink,
            @RequestParam("file") MultipartFile file
    ) throws Exception {
        String key = UUID.randomUUID() + "-" + file.getOriginalFilename();

        Path tempFile = Files.createTempFile("upload-", file.getOriginalFilename());
        file.transferTo(tempFile);

        // 🔹 Agora upload retorna a URL da imagem
        String image = s3Service.uploadFile(tempFile.toFile(), key);

        ProductRequestDTO dto = new ProductRequestDTO(
                name,
                price,
                oldPrice,
                description,
                discountPercentage,
                image, // 🔹 salva a URL, não a key
                affiliateLink

        );

        ProductResponseDTO product = createProductUseCase.execute(dto);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getById(@PathVariable UUID id) {
        ProductResponseDTO product = getProductByIdUseCase.execute(id);
        return ResponseEntity.ok(product);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> list() {
        List<ProductResponseDTO> products = listProductsUseCase.execute();
        return ResponseEntity.ok(products);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductResponseDTO> update(
            @PathVariable UUID id,
            @RequestParam("name") String name,
            @RequestParam("price") Double price,
            @RequestParam(value = "oldPrice", required = false) Double oldPrice,
            @RequestParam("description") String description,
            @RequestParam(value = "discountPercentage", required = false) Double discountPercentage,
            @RequestParam("affiliateLink") String affiliateLink,
            @RequestParam(value = "file", required = false) MultipartFile file
    ) throws IOException {

        ProductRequestDTO dto = new ProductRequestDTO();
        dto.setName(name);
        dto.setPrice(price);
        dto.setOldPrice(oldPrice);
        dto.setDescription(description);
        dto.setDiscountPercentage(discountPercentage);
        dto.setAffiliateLink(affiliateLink);
        dto.setFile(file); // 🔹 passa o arquivo para o use case

        ProductResponseDTO updatedProduct = updateProductUseCase.execute(id, dto);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        // Buscar produto para pegar a URL da imagem
        ProductResponseDTO product = getProductByIdUseCase.execute(id);

        // Extrair a key da URL (remover o https://bucket.s3.region.amazonaws.com/)
        String imageUrl = product.getImage();
        if (imageUrl != null && !imageUrl.isEmpty()) {
            String key = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
            s3Service.deleteFile(key);
        }

        // Deletar produto do banco
        deleteProductUseCase.execute(id);

        return ResponseEntity.noContent().build();
    }
    }