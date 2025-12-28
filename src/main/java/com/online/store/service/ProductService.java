package com.online.store.service;

import com.online.store.dto.product.ProductResponseDto;
import com.online.store.dto.product.ProductRequestDto;
import com.online.store.entity.Category;
import com.online.store.entity.Product;
import com.online.store.entity.status.ProductStatus;
import com.online.store.exception.ResourceNotFoundException;
import com.online.store.mapper.ProductMapper;
import com.online.store.repository.CategoryRepository;
import com.online.store.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ProductBuilder productBuilder;

    public Product getProductEntity(Long id){
        Product product = productRepository.
                findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        return product;
    }

    public ProductResponseDto getProduct(Long id){
        Product product = productRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        return productMapper.toProductDto(product);
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public ProductResponseDto createProduct(ProductRequestDto productRequestDto){
        Product product = productBuilder.build(productRequestDto);
        productRepository.save(product);
        return productMapper.toProductDto(product);
    }

}
