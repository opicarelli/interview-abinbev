package com.opicarelli.abinbev.challenge.dto;

import java.math.BigDecimal;

import com.opicarelli.abinbev.challenge.data.Product;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name="ProductResponse", description="DTO that represents the product contents.")
public class ProductResponse {

    @Schema(description = "Product id", example = "1")
    private Long id;

    @Schema(description = "Product name", example = "Beer One")
    private String name;

    @Schema(description = "Product description", example = "Beer One description")
    private String description;

    @Schema(description = "Product price", example = "1.99")
    private BigDecimal price;

    @Schema(description = "Brand name", example = "Brand One")
    private String brandName;

    public static ProductResponse fromProduct(Product product) {
        ProductResponse response = new ProductResponse();
        response.id = product.getId();
        response.name = product.getName();
        response.description = product.getDescription();
        response.price = product.getPrice();
        response.brandName =  product.getBrand().getName();
        return response;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

}
