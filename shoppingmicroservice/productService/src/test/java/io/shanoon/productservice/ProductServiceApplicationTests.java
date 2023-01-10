package io.shanoon.productservice;

import com.google.gson.Gson;
import io.shanoon.productservice.dto.ProductRequest;
import io.shanoon.productservice.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
@Slf4j
class ProductServiceApplicationTests {

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.4");

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ProductRepository productRepository;

    private static String BASE_URL = "/api/v1/product";



    @DynamicPropertySource
    static void setProperty(DynamicPropertyRegistry dynamicPropertyRegistry){
        dynamicPropertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Test
    void shouldCreateProduct() throws Exception {
        ProductRequest productRequest = getProductRequest();
        String productRequestString = new Gson()
                .toJson(productRequest);

        mockMvc.perform(MockMvcRequestBuilders
                .post(BASE_URL+"/addProduct")
                .contentType(MediaType.APPLICATION_JSON)
                                .content(productRequestString)
                )
                .andDo(print())
                .andExpect(status().isCreated());
        Assertions.assertEquals(1,productRepository.findAll().size());
    }

    @Test
    void shouldGetAllProducts() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                .get(BASE_URL+"/allProducts")
                .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

    private ProductRequest getProductRequest() {
        return ProductRequest.builder()
                .description("iphone 13")
                .name("iphone 13")
                .price(1200.00)
                .build();
    }


}
