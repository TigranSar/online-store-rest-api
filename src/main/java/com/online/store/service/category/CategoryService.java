package com.online.store.service.category;

import com.online.store.dto.category.CategoryResponseDto;
import com.online.store.dto.category.CategoryRequestDto;
import com.online.store.entity.Category;
import com.online.store.exception.NonUniqueDataException;
import com.online.store.exception.ResourceNotFoundException;
import com.online.store.mapper.CategoryMapper;
import com.online.store.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private CategoryRepository categoryRepository;
    private CategoryMapper categoryMapper;

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public CategoryResponseDto create(CategoryRequestDto dto){
        if (categoryRepository.existsByName(dto.getName())){
            throw new NonUniqueDataException("Category with this name is already exists");
        }
        Category category = new Category();
        category.setName(dto.getName());
        return categoryMapper.toCategoryDto(categoryRepository.save(category));
    }

    @Transactional(readOnly = true)
    public Category getCategoryEntity(Long id){
        return categoryRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Category not found"));
    }
}
