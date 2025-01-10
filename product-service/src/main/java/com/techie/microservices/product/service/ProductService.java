package com.techie.microservices.product.service;

import com.techie.microservices.product.dto.ProductRequest;
import com.techie.microservices.product.dto.ProductResponse;
import com.techie.microservices.product.model.Product;
import com.techie.microservices.product.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public ProductResponse createProduct(ProductRequest productRequest){
        List<Product> list = this.productRepository.findAll().stream().toList();
        for(Product product : list){
            if(product.getSkuCode().equals(productRequest.skuCode())){
                product.setDescription(productRequest.description());
                product.setName(productRequest.name());
                product.setPrice(productRequest.price());
                productRepository.save(product);
                return new ProductResponse(product.getId(),product.getName(),product.getDescription(),product.getPrice(),product.getSkuCode());
            }
        }
        Product product = Product.builder().name(productRequest.name())
                                           .description(productRequest.description())
                                           .price(productRequest.price())
                                           .skuCode(productRequest.skuCode())
                                           .build();
        productRepository.save(product);
        log.info("Product created successfully");
        return new ProductResponse(product.getId(),product.getName(),product.getDescription(),product.getPrice(),product.getSkuCode());
    }

    public List<ProductResponse> getProducts(){
        return productRepository.findAll().stream()
                                          .map(product -> new ProductResponse(product.getId(), product.getName(),product.getDescription(),product.getPrice(),product.getSkuCode()))
                                          .toList();
    }

    @Transactional
    public void clearAllProducts() {
        productRepository.deleteAll();  // This will clear all records in the collection
    }
}
