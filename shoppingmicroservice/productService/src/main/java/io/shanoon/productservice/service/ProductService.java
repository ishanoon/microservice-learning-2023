package io.shanoon.productservice.service;

import io.shanoon.productservice.dto.ProductRequest;
import io.shanoon.productservice.dto.ProductResponse;
import io.shanoon.productservice.model.Product;
import io.shanoon.productservice.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public void createProduct(ProductRequest productRequest){
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();
        productRepository.save(product);
        log.info("Product {} is saved", product.getId());
    }

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(product -> ProductResponse.builder()
                        .description(product.getDescription())
                        .price(product.getPrice())
                        .id(product.getId())
                        .name(product.getName())
                        .build())
                .collect(Collectors.toList());
    }
}
