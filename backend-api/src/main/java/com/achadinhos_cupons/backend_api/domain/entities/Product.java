package com.achadinhos_cupons.backend_api.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.UUID;

@Entity
public class Product {

    // === Fields ===
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private Double price;
    private Double oldPrice;
    private String description;
    private Double discountPercentage;
    private String image;
    private String affiliateLink;

    // === Constructor (optional) ===
    public Product(String uuid, String camiseta, double v, double v1, String camisetaDeAlgodão, double v2, String image) {
    }
    protected Product() {}

    public Product(UUID id, String name, Double price, Double oldPrice, String description, Double discountPercentage, String image, String affiliateLink ) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.oldPrice = oldPrice;
        this.description = description;
        this.discountPercentage = discountPercentage;
        this.image = image;
        this.affiliateLink = affiliateLink;

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

    public String getDescription() {
        return description;
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

    public void setDescription(String description) {
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Description cannot be empty.");
        }
        this.description = description.trim();
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

}



