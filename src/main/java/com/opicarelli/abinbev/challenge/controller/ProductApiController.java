package com.opicarelli.abinbev.challenge.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.opicarelli.abinbev.challenge.data.Product;
import com.opicarelli.abinbev.challenge.dto.PageBy;
import com.opicarelli.abinbev.challenge.dto.ProductPaginatedSearchFilter;
import com.opicarelli.abinbev.challenge.dto.ProductRequestCreate;
import com.opicarelli.abinbev.challenge.dto.ProductRequestUpdate;
import com.opicarelli.abinbev.challenge.dto.ProductResponse;
import com.opicarelli.abinbev.challenge.dto.ProductResponseList;
import com.opicarelli.abinbev.challenge.dto.SortBy;
import com.opicarelli.abinbev.challenge.exception.BrandNotFoundException;
import com.opicarelli.abinbev.challenge.exception.ProductNotFoundException;
import com.opicarelli.abinbev.challenge.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import util.SortUtils;

@Validated
@RestController
@RequestMapping("/products")
public class ProductApiController {

    @Autowired
    private ProductService service;

    @Operation(summary = "Get product by id.", security = { @SecurityRequirement(name = "bearer-key") })
    @ApiResponse(responseCode = "200", description = "Returned successfully.", content = @Content(schema = @Schema(implementation = ProductResponse.class)))
    @ApiResponse(responseCode = "400", description = "Bad request.")
    @ApiResponse(responseCode = "404", description = "Product not found.")
    @ApiResponse(responseCode = "500", description = "Internal error.")
    @GetMapping(path = "/id/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<ProductResponse> getById(
            @Schema(description = "Product id",  example = "1") @NotNull @PathVariable("id") Long productId) {
        Product product = service.findById(productId);
        if (product == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        }
        return new ResponseEntity<>(ProductResponse.fromProduct(product), HttpStatus.OK);
    }

    @Operation(summary = "Get product by name.", security = { @SecurityRequirement(name = "bearer-key") })
    @ApiResponse(responseCode = "200", description = "Returned successfully.", content = @Content(schema = @Schema(implementation = ProductResponse.class)))
    @ApiResponse(responseCode = "400", description = "Bad request.")
    @ApiResponse(responseCode = "404", description = "Product not found.")
    @ApiResponse(responseCode = "500", description = "Internal error.")
    @GetMapping(path = "/name/{name}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<ProductResponse> getByName(
            @Schema(description = "Product name",  example = "Beer One") @NotBlank @PathVariable("name") String productName) {
        Product product = service.findByName(productName);
        if (product == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        }
        return new ResponseEntity<>(ProductResponse.fromProduct(product), HttpStatus.OK);
    }

    @Operation(summary = "Create a new product.", security = { @SecurityRequirement(name = "bearer-key") })
    @ApiResponse(responseCode = "200", description = "Returned successfully.", content = @Content(schema = @Schema(implementation = ProductResponse.class)))
    @ApiResponse(responseCode = "400", description = "Bad request.")
    @ApiResponse(responseCode = "500", description = "Internal error.")
    @PostMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<ProductResponse> create(@RequestBody @Valid ProductRequestCreate productDto) {
        try {
            Product product = service.create(productDto);
            return new ResponseEntity<>(ProductResponse.fromProduct(product), HttpStatus.OK);
        } catch (BrandNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Brand not found");
        }
    }

    @Operation(summary = "Update an existent product.", security = { @SecurityRequirement(name = "bearer-key") })
    @ApiResponse(responseCode = "200", description = "Returned successfully.", content = @Content(schema = @Schema(implementation = ProductResponse.class)))
    @ApiResponse(responseCode = "400", description = "Bad request.")
    @ApiResponse(responseCode = "404", description = "Product not found.")
    @ApiResponse(responseCode = "500", description = "Internal error.")
    @PutMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<ProductResponse> update(@RequestBody @Valid ProductRequestUpdate productDto) {
        try {
            Product product = service.update(productDto);
            return new ResponseEntity<>(ProductResponse.fromProduct(product), HttpStatus.OK);
        } catch (ProductNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        } catch (BrandNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Brand not found");
        }
    }

    @Operation(summary = "Delete an existent product.", security = { @SecurityRequirement(name = "bearer-key") })
    @ApiResponse(responseCode = "200", description = "Returned successfully.")
    @ApiResponse(responseCode = "400", description = "Bad request.")
    @ApiResponse(responseCode = "404", description = "Product not found.")
    @ApiResponse(responseCode = "500", description = "Internal error.")
    @DeleteMapping(path = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public void delete(
            @Schema(description = "Product id",  example = "1") @NotNull @PathVariable("id") Long productId) {
        try {
            service.delete(productId);
        } catch (ProductNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        }
    }

    @Cacheable(value = "products",
            key = "{#root.methodName,#productName,#productDescription,#productPriceGreaterThan,#productPriceLessThan,#brandId,#startIndex,#quantityPerPage,#orderColunmName,#orderCriteria}")
    @Operation(summary = "Get products by filters, pagination and orders.", security = { @SecurityRequirement(name = "bearer-key") })
    @ApiResponse(responseCode = "200", description = "Returned successfully.", content = @Content(schema = @Schema(implementation = ProductResponseList.class)))
    @ApiResponse(responseCode = "400", description = "Bad request.")
    @ApiResponse(responseCode = "500", description = "Internal error.")
    @GetMapping(path = "/search", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<ProductResponseList> search(
            @Schema(description = "Product name", example = "Beer One") @Size(max = 50) @RequestParam(name = "name", required = false) String productName,
            @Schema(description = "Description name",  example = "Beer One Description") @Size(max = 200) @RequestParam(name = "description", required = false) String productDescription,
            @Schema(description = "Product price greater than",  example = "1") @RequestParam(name = "priceGreaterThan", required = false) BigDecimal productPriceGreaterThan,
            @Schema(description = "Product price less than",  example = "10") @RequestParam(name = "priceLessThan", required = false) BigDecimal productPriceLessThan,
            @Schema(description = "Brand id",  example = "1") @RequestParam(name = "brandId", required = false) Long brandId,
            @Schema(description = "First element index",  example = "0") @RequestParam(name = "startIndex", defaultValue = "0") Integer startIndex,
            @Schema(description = "Quantity of elements per page",  example = "20") @RequestParam(name = "quantityPerPage", defaultValue = "20") Integer quantityPerPage,
            @Schema(description = "Column name to order the result list", example = "name,price") @RequestParam(name = "orderColumnName", defaultValue = "name") String orderColunmName,
            @Schema(description = "Criteria to order the the column", example = "asc,desc") @RequestParam(name = "orderCriteria", defaultValue = "asc") String orderCriteria) {

        PageBy pageBy = new PageBy.PageByBuilder()
                .startIndex(startIndex)
                .quantityPerPage(quantityPerPage)
                .build();
        
        List<SortBy> sortBy = SortUtils.parseSortBy(orderColunmName, orderCriteria);

        ProductPaginatedSearchFilter filter = new ProductPaginatedSearchFilter.ProductPaginatedSearchFilterBuilder()
                .name(productName)
                .description(productDescription)
                .priceGreaterThan(productPriceGreaterThan)
                .priceLessThan(productPriceLessThan)
                .brandId(brandId)
                .pageBy(pageBy)
                .sortBy(sortBy)
                .build();

        List<Product> products = service.search(filter);
        List<ProductResponse> productsResponse = products.stream()
                .map(ProductResponse::fromProduct)
                .collect(Collectors.toList());
        ProductResponseList response = new ProductResponseList(productsResponse);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
