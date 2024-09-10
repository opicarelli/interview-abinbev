package com.opicarelli.abinbev.challenge.data;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.math.BigDecimal;

import static javax.persistence.GenerationType.IDENTITY;

@Table(name = "products", uniqueConstraints = { @UniqueConstraint(columnNames = "name", name = "products_name_uk") })
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Column(name = "name")
    private String name;

    @Size(max = 200)
    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "price")
    @Min(0)
    private BigDecimal price;

    @NotNull
    @JoinColumn(name = "brand_id", foreignKey = @ForeignKey(name = "products_brand_fk"))
    @ManyToOne(fetch = FetchType.LAZY)
    private Brand brand;

    /**
     * @deprecated JPA only
     */
    @Deprecated
    Product() { }

    /**
     * Use this constructor to create a product.
     *
     * @param name must not be blank
     * @param price the price amount of product must be a positive value
     * @param brand must not be null
     *
     * @throws IllegalArgumentException if any argument is not satisfied
     */
    public Product(@NotBlank String name,
                    @Size(max = 200) String description,
                    @NotNull @Min(0) BigDecimal price,
                    @NotNull Brand brand) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.brand = brand;
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

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

}

