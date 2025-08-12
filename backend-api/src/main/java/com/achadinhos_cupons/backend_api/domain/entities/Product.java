package com.achadinhos_cupons.backend_api.domain.entities;

public class Product {

    // === Fields ===
    private Long id;
    private String name;
    private Double price;
    private Double oldPrice;
    private String description;
    private Double discountPercentage;
    private String image;

    // === Constructor (optional) ===
    public Product() {
    }

    public Product(Long id, String name, Double price, Double oldPrice, String description, Double discountPercentage, String image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.oldPrice = oldPrice;
        this.description = description;
        this.discountPercentage = discountPercentage;
        this.image = image;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

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

    public void setId(Long id) {
        if (id != null && id < 0) {
            throw new IllegalArgumentException("ID must be positive.");
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

}



