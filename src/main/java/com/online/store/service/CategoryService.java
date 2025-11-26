package com.online.store.service;

import com.online.store.dto.category.CategoryResponseDto;
import com.online.store.dto.category.CategoryRequestDto;
import com.online.store.entity.Category;
import com.online.store.mapper.CategoryMapper;
import com.online.store.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryService {
    private CategoryRepository categoryRepository;
    private CategoryMapper categoryMapper;

    public CategoryService(CategoryRepository categoryRepository,
                           CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Transactional
    public CategoryResponseDto createCategory(CategoryRequestDto categoryRequest){
        Category category = new Category();
        category.setName(category.getName());
        return categoryMapper.toCategoryDto(categoryRepository.save(category));
    }
}
