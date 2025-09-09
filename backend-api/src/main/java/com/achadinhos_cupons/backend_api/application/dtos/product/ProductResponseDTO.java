package com.achadinhos_cupons.backend_api.application.dtos.product;

import java.util.UUID;

public class ProductResponseDTO {

    private UUID id;
    private String name;
    private Double price;
    private Double oldPrice;
    private Double discountPercentage;
    private String image;
    private String affiliateLink;
    private Boolean featured;

    public ProductResponseDTO(UUID id, String name, Double price, Double oldPrice,
                              Double discountPercentage, String image, String affiliateLink, Boolean featured) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.oldPrice = oldPrice;
        this.discountPercentage = discountPercentage;
        this.image = image;
        this.affiliateLink = affiliateLink;
        this.featured = featured;
    }



    // getters
    public UUID getId() { return id; }
    public String getName() { return name; }
    public Double getPrice() { return price; }
    public Double getOldPrice() { return oldPrice; }
    public Double getDiscountPercentage() { return discountPercentage; }
    public String getImage() { return image; }
    public String getAffiliateLink() { return affiliateLink; }
    public Boolean getFeatured() {return featured;}
}