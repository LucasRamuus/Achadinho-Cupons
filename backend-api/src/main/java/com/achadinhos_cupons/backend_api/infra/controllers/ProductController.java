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
            @RequestParam(value = "discountPercentage", required = false) Double discountPercentage,
            @RequestParam("affiliateLink") String affiliateLink,
            @RequestParam("featured") Boolean featured,
            @RequestParam("file") MultipartFile file

    ) throws Exception {
        ProductRequestDTO dto = new ProductRequestDTO(
                name, price, oldPrice, discountPercentage, null, affiliateLink, featured
        );
        dto.setFile(file);
        ProductResponseDTO product = createProductUseCase.execute(dto);
        return ResponseEntity.ok(product);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductResponseDTO> update(
            @PathVariable UUID id,
            @RequestParam("name") String name,
            @RequestParam("price") Double price,
            @RequestParam(value = "oldPrice", required = false) Double oldPrice,
            @RequestParam(value = "discountPercentage", required = false) Double discountPercentage,
            @RequestParam("affiliateLink") String affiliateLink,
            @RequestParam("featured") Boolean featured,
            @RequestParam(value = "file", required = false) MultipartFile file
    ) throws IOException {
        ProductRequestDTO dto = new ProductRequestDTO();
        dto.setName(name);
        dto.setPrice(price);
        dto.setOldPrice(oldPrice);
        dto.setDiscountPercentage(discountPercentage);
        dto.setAffiliateLink(affiliateLink);
        dto.setFeatured(featured);
        dto.setFile(file);
        ProductResponseDTO updatedProduct = updateProductUseCase.execute(id, dto);
        return ResponseEntity.ok(updatedProduct);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> list() {
        List<ProductResponseDTO> products = listProductsUseCase.execute();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getById(@PathVariable UUID id) {
        ProductResponseDTO product = getProductByIdUseCase.execute(id);
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        ProductResponseDTO product = getProductByIdUseCase.execute(id);
        deleteProductUseCase.execute(id, product.getImage());
        return ResponseEntity.noContent().build();
    }

}
