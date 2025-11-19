package com.tigran.store.mapper;

import com.tigran.store.dto.product.ProductResponseDto;
import com.tigran.store.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(source = "category.name", target = "categoryName")
    ProductResponseDto toProductDto(Product product);
}
