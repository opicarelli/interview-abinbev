package com.opicarelli.abinbev.challenge.dto;

import java.util.ArrayList;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ProductResponseList", description = "DTO that represents a list of products.")
public class ProductResponseList {

    @Schema(description = "Products list")
    private List<ProductResponse> products = new ArrayList<>();

    public ProductResponseList() {
        // default constructor
    }

    public ProductResponseList(List<ProductResponse> products) {
        this.products = products;
    }

    public List<ProductResponse> getProducts() {
        return products;
    }

    public void setProducts(List<ProductResponse> products) {
        this.products = products;
    }

}
