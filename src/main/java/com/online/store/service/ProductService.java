package com.online.store.service;

import com.online.store.dto.product.ProductResponseDto;
import com.online.store.dto.product.ProductRequestDto;
import com.online.store.entity.Category;
import com.online.store.entity.Product;
import com.online.store.exception.ResourceNotFoundException;
import com.online.store.mapper.ProductMapper;
import com.online.store.repository.CategoryRepository;
import com.online.store.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
        buildProduct(product, productRequest);
        return productMapper.toProductDto(productRepository.save(product));
    }
    @Transactional
    public void deleteProduct(Long id){
        Product product = productRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        productRepository.delete(product);
    }
    @Transactional
    public ProductResponseDto updateProduct(Long id, ProductRequestDto productRequestDto){
        Product product = productRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        buildProduct(product, productRequestDto);
        return productMapper.toProductDto(product);
    }

    @Transactional
    public void setActive(Long id, boolean active) {
        Product product = productRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        product.setActive(active);
    }

    private void buildProduct(Product product, ProductRequestDto productRequest){
        Category category = categoryRepository.
                findById(productRequest.getCategoryId()).
                orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setStockQuantity(productRequest.getStockQuantity());
        product.setCategory(category);
    }
    public List<ProductResponseDto> getAllProduct(){
        List<ProductResponseDto> allAvailableProducts = new ArrayList<>();
        for (Product product : productRepository.findAllByActiveTrue()){
            allAvailableProducts.add(productMapper.toProductDto(product));
        }
        return allAvailableProducts;
    }
}
