package com.opicarelli.abinbev.challenge.repository;

import java.util.List;

import com.opicarelli.abinbev.challenge.data.Product;
import com.opicarelli.abinbev.challenge.dto.ProductPaginatedSearchFilter;

public interface ProductRepositoryCustom {

    List<Product> search(ProductPaginatedSearchFilter filter);

}