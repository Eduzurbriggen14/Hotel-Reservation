package com.example.hotel_reservation.service.Implementation;

import com.example.hotel_reservation.dto.category.CategoryRequestDto;
import com.example.hotel_reservation.dto.category.CategoryResponseDto;
import com.example.hotel_reservation.entity.Category;
import com.example.hotel_reservation.entity.CategoryType;
import com.example.hotel_reservation.mapper.category.CategoryMappers;
import com.example.hotel_reservation.repository.ICategoryRepository;
import com.example.hotel_reservation.service.ICategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImp implements ICategoryService {

    private final ICategoryRepository categoryRepository;
    private final CategoryMappers categoryMappers;

    public CategoryServiceImp(ICategoryRepository categoryRepository, CategoryMappers categoryMappers) {
        this.categoryRepository = categoryRepository;
        this.categoryMappers = categoryMappers;
    }

    @Override
    public CategoryResponseDto createCategory(CategoryRequestDto categoryRequestDto) {
        Category category = categoryMappers.categoryRequestDtoToCategory(categoryRequestDto);
        Category categorySaved = categoryRepository.save(category);

        return categoryMappers.categoryToCategoryResponseDto(categorySaved);
    }

    @Override
    public List<CategoryResponseDto> getAllCategories() {
        List<Category> categoryList = categoryRepository.findAll();
        List<CategoryResponseDto> categoryResponseDto = categoryList.stream()
                .map(categoryMappers::categoryToCategoryResponseDto)
                .toList();
        return categoryResponseDto;
    }

    @Override
    public Category findCategoryById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
        return category;
    }

    @Override
    public Optional<CategoryResponseDto> findCategoryByType(String categoryType) {
        if (categoryType == null || categoryType.isBlank()) return Optional.empty();
        CategoryType type;
        try{
            type = CategoryType.valueOf(categoryType.toUpperCase());
        } catch (IllegalArgumentException e){
            return Optional.empty();
        }
        return categoryRepository.findByCategoryType(type)
                .map(categoryMappers::categoryToCategoryResponseDto);
    }

    @Override
    public boolean existsByCategoryType(Category category) {
        if (category == null || category.getCategoryType() == null) return false;
        CategoryType type = category.getCategoryType();
        return categoryRepository.existsByCategoryType(type);
    }
}
