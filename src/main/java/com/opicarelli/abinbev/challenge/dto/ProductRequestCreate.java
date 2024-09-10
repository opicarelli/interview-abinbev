package com.opicarelli.abinbev.challenge.dto;

import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name="ProductRequestCreate", description="DTO that represents the product to be created.")
public class ProductRequestCreate {

    @Schema(description = "Product name", example = "Beer One")
    @NotBlank
    private String name;

    @Schema(description = "Product description", example = "Beer One description")
    @Size(max = 200)
    private String description;

    @Schema(description = "Product price", example = "1.99")
    @NotNull
    @Min(0)
    private BigDecimal price;

    @Schema(description = "Brand id", example = "1")
    @NotNull
    private Long brandId;

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

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

}
