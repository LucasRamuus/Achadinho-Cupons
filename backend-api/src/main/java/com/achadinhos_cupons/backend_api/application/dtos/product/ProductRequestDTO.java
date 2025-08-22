package com.achadinhos_cupons.backend_api.application.dtos.product;

public class ProductRequestDTO {

    private String name;
    private Double price;
    private Double oldPrice;
    private String description;
    private Double discountPercentage;
    private String image;
    private String affiliateLink;

    public ProductRequestDTO() {}

    public ProductRequestDTO(String name, Double price, Double oldPrice, String description,
                             Double discountPercentage, String image, String affiliateLink) {
        this.name = name;
        this.price = price;
        this.oldPrice = oldPrice;
        this.description = description;
        this.discountPercentage = discountPercentage;
        this.image = image;
        this.affiliateLink = affiliateLink;
    }

    // getters
    public String getName() { return name; }
    public Double getPrice() { return price; }
    public Double getOldPrice() { return oldPrice; }
    public String getDescription() { return description; }
    public Double getDiscountPercentage() { return discountPercentage; }
    public String getImage() { return image; }
    public String getAffiliateLink() { return affiliateLink; }
}