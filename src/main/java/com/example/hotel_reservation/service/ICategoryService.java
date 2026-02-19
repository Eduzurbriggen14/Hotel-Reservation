package com.example.hotel_reservation.service;

import com.example.hotel_reservation.dto.category.CategoryRequestDto;
import com.example.hotel_reservation.dto.category.CategoryResponseDto;
import com.example.hotel_reservation.entity.Category;

import java.util.List;
import java.util.Optional;

public interface ICategoryService {

    CategoryResponseDto createCategory(CategoryRequestDto categoryRequestDto);

    List<CategoryResponseDto> getAllCategories();

    Category findCategoryById(Long id);

    Optional<CategoryResponseDto> findCategoryByType(String categoryType);

    boolean existsByCategoryType(Category category);
}
