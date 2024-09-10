package com.opicarelli.abinbev.challenge;

import org.springframework.beans.factory.annotation.Autowired;

import com.opicarelli.abinbev.challenge.data.Brand;
import com.opicarelli.abinbev.challenge.data.Product;
import com.opicarelli.abinbev.challenge.repository.BrandRepository;
import com.opicarelli.abinbev.challenge.repository.ProductRepository;

public abstract class JpaBase {

    @Autowired
    protected BrandRepository brandRepository;
    
    @Autowired
    protected ProductRepository productRepository;

    protected void saveBrand(Brand brand) {
        brandRepository.save(brand);
    }

    protected void saveProduct(Product product) {
        productRepository.save(product);
    }

    protected void setupDefaultBrands() {
        saveBrand(MockUtils.BRAND_A);
        saveBrand(MockUtils.BRAND_B);
        saveBrand(MockUtils.BRAND_C);
    }

    protected void setupDefaultProducts() {
        saveProduct(MockUtils.PRODUCT_A);
        saveProduct(MockUtils.PRODUCT_B);
        saveProduct(MockUtils.PRODUCT_C);
    }

    protected BrandRepository getBrandRepository() {
        return brandRepository;
    }

    protected ProductRepository getProductRepository() {
        return productRepository;
    }
}