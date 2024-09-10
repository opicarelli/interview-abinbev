package com.opicarelli.abinbev.challenge.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.opicarelli.abinbev.challenge.data.Brand;
import com.opicarelli.abinbev.challenge.data.Product;
import com.opicarelli.abinbev.challenge.dto.ProductPaginatedSearchFilter;
import com.opicarelli.abinbev.challenge.dto.ProductRequestCreate;
import com.opicarelli.abinbev.challenge.dto.ProductRequestUpdate;
import com.opicarelli.abinbev.challenge.exception.BrandNotFoundException;
import com.opicarelli.abinbev.challenge.exception.ProductNotFoundException;
import com.opicarelli.abinbev.challenge.repository.BrandRepository;
import com.opicarelli.abinbev.challenge.repository.ProductRepository;
import com.opicarelli.abinbev.challenge.repository.ProductRepositoryCustom;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;
    
    @Autowired
    private ProductRepositoryCustom repositoryCustom;

    @Autowired
    private BrandRepository brandRepository;

    public Product findById(Long id) {
        Optional<Product> optional = repository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    public Product findByName(String name) {
        Optional<Product> optional = repository.findByName(name);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    public Product create(ProductRequestCreate productDto) throws BrandNotFoundException {
        Optional<Brand> brandOptional = brandRepository.findById(productDto.getBrandId());
        if (!brandOptional.isPresent()) {
            throw new BrandNotFoundException();
        }
        Brand brand = brandOptional.get();
        Product product = new Product(productDto.getName(), productDto.getDescription(), productDto.getPrice(), brand);
        return save(product);
    }

    public Product update(ProductRequestUpdate productDto) throws ProductNotFoundException, BrandNotFoundException {
        Optional<Product> productOptional = repository.findById(productDto.getId());
        if (!productOptional.isPresent()) {
            throw new ProductNotFoundException();
        }
        Product product = productOptional.get();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        Optional<Brand> brandOptional = brandRepository.findById(productDto.getBrandId());
        if (!brandOptional.isPresent()) {
            throw new BrandNotFoundException();
        }
        Brand brand = brandOptional.get();
        product.setBrand(brand);
        return save(product);
    }

    public Product save(Product product) {
        return repository.save(product);
    }

    public void delete(Long id) throws ProductNotFoundException {
        Optional<Product> productOptional = repository.findById(id);
        if (!productOptional.isPresent()) {
            throw new ProductNotFoundException();
        }
        repository.delete(productOptional.get());
    }

    public List<Product> search(ProductPaginatedSearchFilter filter) {
        return repositoryCustom.search(filter);
    }

}
