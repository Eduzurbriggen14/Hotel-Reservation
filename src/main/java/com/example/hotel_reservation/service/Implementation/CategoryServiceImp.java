package com.example.hotel_reservation.service.Implementation;

import com.example.hotel_reservation.dto.category.CategoryRequestDto;
import com.example.hotel_reservation.dto.category.CategoryResponseDto;
import com.example.hotel_reservation.entity.Category;
import com.example.hotel_reservation.entity.CategoryType;
import com.example.hotel_reservation.exception.BadRequestException;
import com.example.hotel_reservation.exception.ResourceNotFoundException;
import com.example.hotel_reservation.mapper.CategoryMappers;
import com.example.hotel_reservation.repository.ICategoryRepository;
import com.example.hotel_reservation.service.ICategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImp implements ICategoryService {

    private final ICategoryRepository categoryRepository;

    public CategoryServiceImp(ICategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryResponseDto createCategory(CategoryRequestDto categoryRequestDto) {
        if (categoryRequestDto == null) {
            throw new BadRequestException("Category data cannot be null");
        }

        if (categoryRequestDto.categoryType() == null || categoryRequestDto.categoryType().isBlank()) {
            throw new BadRequestException("Category type is required");
        }

        try {
            CategoryType type = CategoryType.valueOf(categoryRequestDto.categoryType().toUpperCase());

            // Verificar si ya existe una categoría con este tipo
            if (categoryRepository.existsByCategoryType(type)) {
                throw new BadRequestException("Category with type '" + type + "' already exists");
            }
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Invalid category type: '" + categoryRequestDto.categoryType() + "'. Valid types are: SIMPLE, DOUBLE, SUITE", e);
        }

        Category category = CategoryMappers.categoryRequestDtoToCategory(categoryRequestDto);
        Category savedCategory = categoryRepository.save(category);

        return CategoryMappers.categoryToCategoryResponseDto(savedCategory);
    }

    @Override
    public List<CategoryResponseDto> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(CategoryMappers::categoryToCategoryResponseDto)
                .toList();
    }

    @Override
    public Category findCategoryById(Long id) {
        if (id == null || id <= 0) {
            throw new BadRequestException("Category ID must be a valid positive number");
        }

        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category with ID " + id + " not found"));
    }

    @Override
    public Optional<CategoryResponseDto> findCategoryByType(String categoryType) {
        if (categoryType == null || categoryType.isBlank()) {
            throw new BadRequestException("Category type cannot be null or empty");
        }

        try {
            CategoryType type = CategoryType.valueOf(categoryType.toUpperCase());
            return categoryRepository.findByCategoryType(type)
                    .map(CategoryMappers::categoryToCategoryResponseDto);
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Invalid category type: '" + categoryType + "'. Valid types are: SIMPLE, DOUBLE, SUITE", e);
        }
    }

    @Override
    public boolean existsByCategoryType(Category category) {
        if (category == null || category.getCategoryType() == null) {
            throw new BadRequestException("Category and category type cannot be null");
        }
        return categoryRepository.existsByCategoryType(category.getCategoryType());
    }
}
