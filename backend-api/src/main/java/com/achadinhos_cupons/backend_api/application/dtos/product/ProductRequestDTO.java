package com.achadinhos_cupons.backend_api.application.dtos.product;

import org.springframework.web.multipart.MultipartFile;

public class ProductRequestDTO {

    private String name;
    private Double price;
    private Double oldPrice;
    private Double discountPercentage;
    private String image;
    private String affiliateLink;
    private Boolean featured;
    private MultipartFile file;

    // Construtor vazio
    public ProductRequestDTO() {}

    // Construtor sem MultipartFile (para quando já tiver a URL da imagem)
    public ProductRequestDTO(String name, Double price, Double oldPrice,
                             Double discountPercentage, String image, String affiliateLink, Boolean featured) {
        this.name = name;
        this.price = price;
        this.oldPrice = oldPrice;
        this.discountPercentage = discountPercentage;
        this.image = image;
        this.affiliateLink = affiliateLink;
        this.featured = featured;
    }

    // Construtor com MultipartFile (para quando precisar do upload)
    public ProductRequestDTO(String name, Double price, Double oldPrice,
                             Double discountPercentage, String image, String affiliateLink, MultipartFile file, Boolean featured) {
        this(name, price, oldPrice, discountPercentage, image, affiliateLink, featured);
        this.file = file;
    }




    // Getters
    public String getName() { return name; }
    public Double getPrice() { return price; }
    public Double getOldPrice() { return oldPrice; }
    public Double getDiscountPercentage() { return discountPercentage; }
    public String getImage() { return image; }
    public String getAffiliateLink() { return affiliateLink; }
    public MultipartFile getFile() { return file; }
    public Boolean getFeatured() {return featured;}

    public void setName(String name) { this.name = name; }
    public void setPrice(Double price) { this.price = price; }
    public void setOldPrice(Double oldPrice) { this.oldPrice = oldPrice; }
    public void setDiscountPercentage(Double discountPercentage) { this.discountPercentage = discountPercentage; }
    public void setImage(String image) { this.image = image; }
    public void setAffiliateLink(String affiliateLink) { this.affiliateLink = affiliateLink; }
    public void setFeatured(Boolean featured) {this.featured = featured;}
    public void setFile(MultipartFile file) { this.file = file; }

}
