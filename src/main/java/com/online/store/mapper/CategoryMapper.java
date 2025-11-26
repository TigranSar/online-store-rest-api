package com.online.store.mapper;

import com.online.store.dto.category.CategoryResponseDto;
import com.online.store.entity.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface CategoryMapper {

    CategoryResponseDto toCategoryDto(Category category);
}
