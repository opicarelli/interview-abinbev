package com.opicarelli.abinbev.challenge;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.opicarelli.abinbev.challenge.data.Brand;
import com.opicarelli.abinbev.challenge.data.Product;

@Component
public class MockUtils {

    public static final Brand BRAND_A = new Brand("Brand A");
    public static final Brand BRAND_B = new Brand("Brand B");
    public static final Brand BRAND_C = new Brand("Brand C");
    
    public static final Product PRODUCT_A = new Product("Product A", "Product A Description", BigDecimal.ONE, BRAND_A);
    public static final Product PRODUCT_B = new Product("Product B", "Product B Description", BigDecimal.ONE, BRAND_B);
    public static final Product PRODUCT_C = new Product("Product C", "Product C Description", BigDecimal.TEN, BRAND_C);
}