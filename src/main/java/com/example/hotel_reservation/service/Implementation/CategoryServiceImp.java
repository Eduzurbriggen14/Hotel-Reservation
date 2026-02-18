package com.example.hotel_reservation.service.Implementation;

import com.example.hotel_reservation.dto.category.CategoryRequestDto;
import com.example.hotel_reservation.dto.category.CategoryResponseDto;
import com.example.hotel_reservation.entity.Category;
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
        return List.of();
    }

    @Override
    public Optional<CategoryResponseDto> findCategoryById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<CategoryResponseDto> findCategoryByType(String categoryType) {
        return Optional.empty();
    }

    @Override
    public boolean existsByCategoryType(String categoryType) {
        return false;
    }
}
