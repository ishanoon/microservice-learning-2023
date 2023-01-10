package io.shanoon.productservice.controller;

import io.shanoon.productservice.dto.ProductRequest;
import io.shanoon.productservice.dto.ProductResponse;
import io.shanoon.productservice.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/product")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/addProduct")
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody ProductRequest productRequest){
        productService.createProduct(productRequest);
    }

    @GetMapping("/allProducts")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllProducts(){
        return productService.getAllProducts();
    }
}
