package com.online.store.service.product;

import com.online.store.dto.product.ProductRequestDto;
import com.online.store.entity.Category;
import com.online.store.entity.Product;
import com.online.store.entity.status.ProductStatus;
import com.online.store.service.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductBuilder {
    private final CategoryService categoryService;

    public Product build(ProductRequestDto productRequestDto){
        Category category = categoryService.getCategoryEntity(productRequestDto.getCategoryId());
        Product product = new Product();
        product.setName(productRequestDto.getName());
        product.setCategory(category);
        product.setPrice(productRequestDto.getPrice());
        product.setDescription(productRequestDto.getDescription());
        product.setStockQuantity(productRequestDto.getStockQuantity());
        product.setStatus(ProductStatus.ACTIVE);
        return product;
    }

}
