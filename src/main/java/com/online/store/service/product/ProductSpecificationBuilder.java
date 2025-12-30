package com.online.store.service.product;

import com.online.store.entity.Category;
import com.online.store.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class ProductSpecificationBuilder {

    public Specification<Product> build(String productName,
                                        Category category,
                                        BigDecimal maxPrice,
                                        BigDecimal minPrice){
        return Specification.allOf(
                ProductSpecification.categoryOf(category),
                ProductSpecification.hasName(productName),
                ProductSpecification.priceFrom(minPrice),
                ProductSpecification.priceTo(maxPrice)
        );
    }
}
