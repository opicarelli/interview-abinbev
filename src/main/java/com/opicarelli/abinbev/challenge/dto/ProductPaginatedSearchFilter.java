package com.opicarelli.abinbev.challenge.dto;

import java.math.BigDecimal;
import java.util.List;

public class ProductPaginatedSearchFilter {

    private String name;
    private String description;
    private BigDecimal priceGreaterThan;
    private BigDecimal priceLessThan;
    private Long brandId;
    private PageBy pageBy;
    private List<SortBy> sortBy;

    public ProductPaginatedSearchFilter(ProductPaginatedSearchFilterBuilder builder) {
        this.name = builder.name;
        this.description = builder.description;
        this.priceGreaterThan = builder.priceGreaterThan;
        this.priceLessThan = builder.priceLessThan;
        this.brandId = builder.brandId;
        this.pageBy = builder.pageBy;
        this.sortBy = builder.sortBy;
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

    public BigDecimal getPriceGreaterThan() {
        return priceGreaterThan;
    }

    public void setPriceGreaterThan(BigDecimal priceGreaterThan) {
        this.priceGreaterThan = priceGreaterThan;
    }

    public BigDecimal getPriceLessThan() {
        return priceLessThan;
    }

    public void setPriceLessThan(BigDecimal priceLessThan) {
        this.priceLessThan = priceLessThan;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public PageBy getPageBy() {
        return pageBy;
    }

    public void setPageBy(PageBy pageBy) {
        this.pageBy = pageBy;
    }

    public List<SortBy> getSortBy() {
        return sortBy;
    }

    public void setSortBy(List<SortBy> sortBy) {
        this.sortBy = sortBy;
    }

    public static class ProductPaginatedSearchFilterBuilder {

        private String name;
        private String description;
        private BigDecimal priceGreaterThan;
        private BigDecimal priceLessThan;
        private Long brandId;
        private PageBy pageBy;
        private List<SortBy> sortBy;

        public ProductPaginatedSearchFilterBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ProductPaginatedSearchFilterBuilder description(String description) {
            this.description = description;
            return this;
        }

        public ProductPaginatedSearchFilterBuilder priceGreaterThan(BigDecimal priceGreaterThan) {
            this.priceGreaterThan = priceGreaterThan;
            return this;
        }

        public ProductPaginatedSearchFilterBuilder priceLessThan(BigDecimal priceLessThan) {
            this.priceLessThan = priceLessThan;
            return this;
        }

        public ProductPaginatedSearchFilterBuilder brandId(Long brandId) {
            this.brandId = brandId;
            return this;
        }

        public ProductPaginatedSearchFilterBuilder pageBy(PageBy pageBy) {
            this.pageBy = pageBy;
            return this;
        }

        public ProductPaginatedSearchFilterBuilder sortBy(List<SortBy> sortBy) {
            this.sortBy = sortBy;
            return this;
        }

        public ProductPaginatedSearchFilter build() {
            return new ProductPaginatedSearchFilter(this);
        }

    }
}
