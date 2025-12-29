package com.online.store.service.product;

import com.online.store.dto.product.ProductResponseDto;
import com.online.store.dto.product.ProductRequestDto;
import com.online.store.entity.Category;
import com.online.store.entity.Product;
import com.online.store.entity.status.ProductStatus;
import com.online.store.exception.InvalidProductStatusTransitionException;
import com.online.store.exception.ProductAlreadyInactiveException;
import com.online.store.exception.ResourceNotFoundException;
import com.online.store.mapper.ProductMapper;
import com.online.store.repository.ProductRepository;
import com.online.store.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ProductBuilder productBuilder;
    private final ProductSpecificationBuilder productSpecificationBuilder;
    private final CategoryService categoryService;

    public Product getProductEntity(Long id){
        return productRepository.
                findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }


    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public ProductResponseDto create(ProductRequestDto productRequestDto){
        Product product = productBuilder.build(productRequestDto);
        productRepository.save(product);
        return productMapper.toProductDto(product);
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public void deactivate(Long id){
        Product product = getProductEntity(id);
        if (product.getStatus() == ProductStatus.INACTIVE){
            throw new InvalidProductStatusTransitionException("Product is already inactive");
        }
        product.setStatus(ProductStatus.INACTIVE);
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public void activate(Long id){
        Product product = getProductEntity(id);
        if (product.getStatus() == ProductStatus.ACTIVE){
            throw new InvalidProductStatusTransitionException("Product is already active");
        }
        product.setStatus(ProductStatus.ACTIVE);
    }

    @Transactional(readOnly = true)
    public Page<Product> getAll(String productName,
                                     Long categoryId,
                                     BigDecimal maxPrice,
                                     BigDecimal minPrice,
                                     Pageable pageable){
        Category category = categoryId == null
                ? null
                : categoryService.getCategoryEntity(categoryId);

        Specification<Product> specification = productSpecificationBuilder
                .build(productName,category,maxPrice,minPrice);
        return productRepository.findAllByActiveTrue(specification,pageable);
    }

    @Transactional(readOnly = true)
    public ProductResponseDto get(Long id){
        Product product = productRepository.findByIdAndActiveTrue(id).
                orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        return productMapper.toProductDto(product);
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public ProductResponseDto update(Long id, ProductRequestDto dto){
        Product product = productRepository.findByIdAndActiveTrue(id)
                .orElseThrow(()-> new ResourceNotFoundException("Product not found"));
        Category category = categoryService.getCategoryEntity(dto.getCategoryId());
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStockQuantity(dto.getStockQuantity());
        product.setCategory(category);
        return productMapper.toProductDto(product);
    }
}
