package com.achadinhos_cupons.backend_api.application.dtos.product;

import java.util.UUID;

public class ProductResponseDTO {

    private UUID id;
    private String name;
    private Double price;
    private Double oldPrice;
    private String description;
    private Double discountPercentage;
    private String image;
    private String affiliateLink;

    public ProductResponseDTO(UUID id, String name, Double price, Double oldPrice, String description,
                              Double discountPercentage, String image, String affiliateLink) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.oldPrice = oldPrice;
        this.description = description;
        this.discountPercentage = discountPercentage;
        this.image = image;
        this.affiliateLink = affiliateLink;
    }

    // getters
    public UUID getId() { return id; }
    public String getName() { return name; }
    public Double getPrice() { return price; }
    public Double getOldPrice() { return oldPrice; }
    public String getDescription() { return description; }
    public Double getDiscountPercentage() { return discountPercentage; }
    public String getImage() { return image; }
    public String getAffiliateLink() { return affiliateLink; }
}