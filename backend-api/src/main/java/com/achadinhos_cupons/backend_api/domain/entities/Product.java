package com.achadinhos_cupons.backend_api.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.UUID;
import jakarta.persistence.Column;

@Entity
public class Product {

    // === Fields ===
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private Double price;
    @Column(name = "old_price")
    private Double oldPrice;
    @Column(name = "discount_percentage")
    private Double discountPercentage;
    private String image;
    @Column(name = "affiliate_link")
    private String affiliateLink;
    private Boolean featured;

    protected Product() {}

    public Product(UUID id, String name, Double price, Double oldPrice, Double discountPercentage, String image, String affiliateLink, Boolean featured ) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.oldPrice = oldPrice;
        this.discountPercentage = discountPercentage;
        this.image = image;
        this.affiliateLink = affiliateLink;
        this.featured = featured;

    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() { return price; }

    public Double getOldPrice() {
        return oldPrice;
    }

    public Double getDiscountPercentage() {
        return discountPercentage;
    }

    public String getImage() {
        return image;
    }

    public String getAffiliateLink() {
        return affiliateLink;
    }

    public Boolean getFeatured() { return featured; }


    public void setId(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }

        // Verifica se é um UUID válido (não o nil UUID)
        if (id.equals(new UUID(0L, 0L))) { // UUID nil: 00000000-0000-0000-0000-000000000000
            throw new IllegalArgumentException("ID cannot be nil UUID");
        }

        this.id = id;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty.");
        }
        this.name = name.trim();
    }

    public void setPrice(Double price) {
        if (price == null || price < 0) {
            throw new IllegalArgumentException("Price must be a non-negative value.");
        }
        this.price = price;
    }


    public void setOldPrice(Double oldPrice) {
        if (oldPrice != null && oldPrice < 0) {
            throw new IllegalArgumentException("Old price must be a non-negative value.");
        }
        this.oldPrice = oldPrice;
    }


    public void setDiscountPercentage(Double discountPercentage) {
        if (discountPercentage == null) {
            this.discountPercentage = 0.0;
            return;
        }

        if (discountPercentage < 0 || discountPercentage > 100) {
            throw new IllegalArgumentException("Discount percentage must be between 0 and 100.");
        }
        this.discountPercentage = discountPercentage;
    }

    public void setImage(String image) {
        this.image = image != null ? image.trim() : null;
    }

    public void setAffiliateLink(String affiliateLink) {
        if (affiliateLink == null || affiliateLink.trim().isEmpty()) {
            throw new IllegalArgumentException("Affiliate link cannot be empty.");
        }
        this.affiliateLink = affiliateLink.trim();
    }

    public void setFeatured(Boolean featured) { this.featured = featured; }

}



