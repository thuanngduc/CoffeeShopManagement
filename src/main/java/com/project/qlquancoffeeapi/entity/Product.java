package com.project.qlquancoffeeapi.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Long id;

    @NotEmpty(message = "Product name cannot be empty")
    @JsonProperty("itemName")
    private String itemName;
    @JsonProperty("description")
    private String description;
    @NotNull(message = "Price cannot be empty")
    @Min(value = 0, message = "Price must be greater than or equal to 0")
    @JsonProperty("price")
    private Double price;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Product type cannot be empty")
    @JsonProperty("productType")
    private ProductType productType;
    @JsonProperty("imageUrl")
    private String imageUrl;


    public Product() {
    }

    public Product(Long id, String itemName, String description, @NotNull(message = "Price cannot be empty") Double price, @NotNull(message = "Product type cannot be empty") ProductType productType, String imageUrl) {
        this.id = id;
        this.itemName = itemName;
        this.description = description;
        this.price = price;
        this.productType = productType;
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
