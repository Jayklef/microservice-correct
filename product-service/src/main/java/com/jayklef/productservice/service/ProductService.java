package com.jayklef.productservice.service;

import com.jayklef.productservice.dto.ProductDto;
import com.jayklef.productservice.dto.ProductResponseDto;
import com.jayklef.productservice.model.Product;
import com.jayklef.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    public void createProduct(ProductDto productDto){
        Product product = Product.builder()
                .name(productDto.getName())
                .description(productDto.getDescription())
                .price(productDto.getPrice())
                .build();

        productRepository.save(product);
        log.info("Product with Id of {} is saved", product.getId());
    }

    public List<ProductResponseDto> getAllProducts() {
        List<Product> products = productRepository.findAll();

       return products.stream().map(product -> mapToProductResponseDto(product)).toList();
    }

    private ProductResponseDto mapToProductResponseDto(Product product) {
        return ProductResponseDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }
}
