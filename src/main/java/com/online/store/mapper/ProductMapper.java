package com.online.store.mapper;

import com.online.store.dto.product.ProductResponseDto;
import com.online.store.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(source = "category.name", target = "categoryName")
    ProductResponseDto toProductDto(Product product);
}
