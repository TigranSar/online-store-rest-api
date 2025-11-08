package com.tigran.store.mapper;

import com.tigran.store.dto.v1.category.CategoryResponseDto;
import com.tigran.store.entity.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface CategoryMapper {

    CategoryResponseDto toCategoryDto(Category category);
}
