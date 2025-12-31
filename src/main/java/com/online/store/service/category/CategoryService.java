package com.online.store.service.category;

import com.online.store.dto.category.CategoryResponseDto;
import com.online.store.dto.category.CategoryRequestDto;
import com.online.store.entity.Category;
import com.online.store.entity.status.CategoryStatus;
import com.online.store.exception.NonEmptyProductsOfCategoryException;
import com.online.store.exception.NonUniqueDataException;
import com.online.store.exception.ResourceNotFoundException;
import com.online.store.mapper.CategoryMapper;
import com.online.store.repository.CategoryRepository;
import com.online.store.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private CategoryRepository categoryRepository;
    private CategoryMapper categoryMapper;
    private ProductRepository productRepository;

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public CategoryResponseDto create(CategoryRequestDto dto){
        if (categoryRepository.existsByName(dto.getName())){
            throw new NonUniqueDataException("Category with this name is already exists");
        }
        Category category = new Category();
        category.setName(dto.getName());
        category.setStatus(CategoryStatus.ACTIVE);
        return categoryMapper.toCategoryDto(categoryRepository.save(category));
    }

    @Transactional(readOnly = true)
    public Category getCategoryEntity(Long id){
        return categoryRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Category not found"));
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public void deactivate(Long id){
        Category category = getCategoryEntity(id);
        if (!productRepository.existsByCategoryId(id)){
            throw new NonEmptyProductsOfCategoryException("Category has products, you can't delete in this case");
        }
        category.setStatus(CategoryStatus.INACTIVE);
    }
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public void activate(Long id){
        Category category = getCategoryEntity(id);
        category.setStatus(CategoryStatus.ACTIVE);
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public CategoryResponseDto update(Long id, CategoryRequestDto dto){
        Category category = getCategoryEntity(id);
        category.setName(dto.getName());
        return categoryMapper.toCategoryDto(categoryRepository.save(category));
    }

    @Transactional(readOnly = true)
    public Page<CategoryResponseDto> getAll(Pageable pageable){
        return categoryRepository.findAllByActiveTrue(pageable)
                .map(categoryMapper::toCategoryDto);
    }
}
