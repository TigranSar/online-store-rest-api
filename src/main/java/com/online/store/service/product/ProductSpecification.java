package com.online.store.service.product;

import com.online.store.entity.Category;
import com.online.store.entity.Product;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class ProductSpecification {
    public static Specification<Product> hasName(String productName) {
        return (root, query, cb) ->
                productName == null ? null : cb.equal(root.get("name"), productName);
    }

    public static Specification<Product> priceFrom(BigDecimal minPrice) {
        return (root, query, cb) ->
                minPrice == null ? null : cb.greaterThanOrEqualTo(root.get("price"), minPrice);
    }

    public static Specification<Product> priceTo(BigDecimal maxPrice) {
        return (root, query, cb) ->
                maxPrice == null ? null : cb.lessThanOrEqualTo(root.get("price"), maxPrice);
    }

    public static Specification<Product> categoryOf(Category category) {
        return (root, query, cb) ->
                category == null ? null : cb.equal(root.get("category"),category);
    }
}
