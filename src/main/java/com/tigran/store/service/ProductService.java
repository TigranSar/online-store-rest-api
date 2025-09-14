package com.tigran.store.service;

import com.tigran.store.dto.ProductResponseDto;
import com.tigran.store.dto.request.ProductRequestDto;
import com.tigran.store.entity.Category;
import com.tigran.store.entity.Product;
import com.tigran.store.exception.ResourceNotFoundException;
import com.tigran.store.mapper.ProductMapper;
import com.tigran.store.repository.CategoryRepository;
import com.tigran.store.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
public class ProductService {
    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;
    private ProductMapper productMapper;

    public ProductService(ProductRepository productRepository,
                          CategoryRepository categoryRepository,
                          ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.productMapper = productMapper;
    }
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
    public ProductResponseDto createProduct(ProductRequestDto productRequest){
        Product product = new Product();
        Category category = categoryRepository.
                findById(productRequest.getCategoryId()).
                orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setStockQuantity(productRequest.getStockQuantity());
        product.setCategory(category);
        product.setOrderItems(new ArrayList<>());
        return productMapper.toProductDto(productRepository.save(product));
    }

}
