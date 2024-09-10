package com.opicarelli.abinbev.challenge;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.ArrayList;

import javax.annotation.PostConstruct;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opicarelli.abinbev.challenge.dto.ProductRequestCreate;
import com.opicarelli.abinbev.challenge.dto.ProductRequestUpdate;
import com.opicarelli.abinbev.challenge.service.JwtService;

@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK, classes = CodeChallengeInterviewApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class ProductApiIntegrationTest extends JpaBase {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtService jwtService;

    @PostConstruct
    public void setUp() {
        setupDefaultBrands();
        setupDefaultProducts();
    }

    @Test
    public void givenProducts_whenGetProductById_thenStatus200() throws Exception {
        String token = jwtService.generateToken(new User("admin", "password", new ArrayList<>()));
        mvc.perform(get("/products/id/1")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is(MockUtils.PRODUCT_A.getName())))
                .andExpect(jsonPath("$.description", is(MockUtils.PRODUCT_A.getDescription())))
                .andExpect(jsonPath("$.brandName", is(MockUtils.PRODUCT_A.getBrand().getName())));
    }

    @Test
    public void givenProducts_whenGetInvalidProductById_thenStatus404() throws Exception {
        String token = jwtService.generateToken(new User("admin", "password", new ArrayList<>()));
        mvc.perform(get("/products/id/10000")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isNotFound());
    }

    @Test
    public void givenProducts_whenGetProductByName_thenStatus200() throws Exception {
        String token = jwtService.generateToken(new User("admin", "password", new ArrayList<>()));
        mvc.perform(get("/products/name/Product A")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is(MockUtils.PRODUCT_A.getName())))
                .andExpect(jsonPath("$.description", is(MockUtils.PRODUCT_A.getDescription())))
                .andExpect(jsonPath("$.brandName", is(MockUtils.PRODUCT_A.getBrand().getName())));
    }

    @Test
    public void givenProducts_whenGetInvalidProductByName_thenStatus404() throws Exception {
        String token = jwtService.generateToken(new User("admin", "password", new ArrayList<>()));
        mvc.perform(get("/products/name/Product XYZ")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isNotFound());
    }

    @Test
    public void givenProductDto_whenPostProduct_thenStatus200() throws Exception {
        String token = jwtService.generateToken(new User("admin", "password", new ArrayList<>()));
        ProductRequestCreate productDto = new ProductRequestCreate();
        productDto.setName("Product New");
        productDto.setDescription("Product New for unit test");
        productDto.setPrice(BigDecimal.TEN);
        productDto.setBrandId(1L);
        mvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
                .content(objectMapper.writeValueAsString(productDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.name", is(productDto.getName())))
                .andExpect(jsonPath("$.description", is(productDto.getDescription())))
                .andExpect(jsonPath("$.brandName", notNullValue()));
    }

    @Test
    public void givenProductDto_whenPostEmptyProduct_thenStatus400() throws Exception {
        String token = jwtService.generateToken(new User("admin", "password", new ArrayList<>()));
        ProductRequestCreate productDto = new ProductRequestCreate();
        mvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
                .content(objectMapper.writeValueAsString(productDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void givenProductDto_whenPutProduct_thenStatus200() throws Exception {
        String token = jwtService.generateToken(new User("admin", "password", new ArrayList<>()));
        ProductRequestUpdate productDto = new ProductRequestUpdate();
        productDto.setId(1L);
        productDto.setName("Product A (new name)");
        productDto.setDescription("Product A Description (new description)");
        productDto.setPrice(BigDecimal.TEN);
        productDto.setBrandId(2L);
        mvc.perform(put("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
                .content(objectMapper.writeValueAsString(productDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is(productDto.getName())))
                .andExpect(jsonPath("$.description", is(productDto.getDescription())))
                .andExpect(jsonPath("$.brandName", notNullValue()));
    }

    @Test
    public void givenProductDto_whenPutEmptyProduct_thenStatus400() throws Exception {
        String token = jwtService.generateToken(new User("admin", "password", new ArrayList<>()));
        ProductRequestUpdate productDto = new ProductRequestUpdate();
        mvc.perform(put("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
                .content(objectMapper.writeValueAsString(productDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void givenProductDto_whenDeleteProduct_thenStatus200() throws Exception {
        String token = jwtService.generateToken(new User("admin", "password", new ArrayList<>()));
        mvc.perform(delete("/products/3")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    public void givenProductDto_whenDeleteInvalidProduct_thenStatus404() throws Exception {
        String token = jwtService.generateToken(new User("admin", "password", new ArrayList<>()));
        mvc.perform(delete("/products/10000")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isNotFound());
    }

    @Test
    public void givenProducts_whenSearchProductByEmptyFilter_thenStatus200() throws Exception {
        String token = jwtService.generateToken(new User("admin", "password", new ArrayList<>()));
        mvc.perform(get("/products/search")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.products", hasSize(3)));
    }

    @Test
    public void givenProducts_whenSearchProductByNameFilter_thenStatus200() throws Exception {
        String token = jwtService.generateToken(new User("admin", "password", new ArrayList<>()));
        mvc.perform(get("/products/search")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
                .queryParam("name", "Product"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.products", hasSize(3)));
    }

    @Test
    public void givenProducts_whenSearchProductByDescriptionFilter_thenStatus200() throws Exception {
        String token = jwtService.generateToken(new User("admin", "password", new ArrayList<>()));
        mvc.perform(get("/products/search")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
                .queryParam("description", "description"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.products", hasSize(3)));
    }

    @Test
    public void givenProducts_whenSearchProductByPriceFilter_thenStatus200() throws Exception {
        String token = jwtService.generateToken(new User("admin", "password", new ArrayList<>()));
        mvc.perform(get("/products/search")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
                .queryParam("priceGreaterThan", "1")
                .queryParam("priceLessThan", "5"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.products", hasSize(2)));
    }

    @Test
    public void givenProducts_whenSearchProductByBrandFilter_thenStatus200() throws Exception {
        String token = jwtService.generateToken(new User("admin", "password", new ArrayList<>()));
        mvc.perform(get("/products/search")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
                .queryParam("brandId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.products", hasSize(1)));
    }

    @Test
    public void givenProducts_whenSearchProductByDefaultOrder_thenStatus200() throws Exception {
        String token = jwtService.generateToken(new User("admin", "password", new ArrayList<>()));
        mvc.perform(get("/products/search")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.products[0].name", is(MockUtils.PRODUCT_A.getName())));
    }

    @Test
    public void givenProducts_whenSearchProductByPriceDescOrder_thenStatus200() throws Exception {
        String token = jwtService.generateToken(new User("admin", "password", new ArrayList<>()));
        mvc.perform(get("/products/search")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
                .queryParam("orderColumnName", "price")
                .queryParam("orderCriteria", "desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.products[0].name", is(MockUtils.PRODUCT_C.getName())));
    }

    @Test
    public void givenProducts_whenSearchProductByPagination_thenStatus200() throws Exception {
        String token = jwtService.generateToken(new User("admin", "password", new ArrayList<>()));
        mvc.perform(get("/products/search")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
                .queryParam("startIndex", "0")
                .queryParam("quantityPerPage", "2"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.products", hasSize(2)));
    }

}
