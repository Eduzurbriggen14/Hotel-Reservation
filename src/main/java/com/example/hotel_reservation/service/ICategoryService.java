package com.example.hotel_reservation.service;

import com.example.hotel_reservation.dto.category.CategoryRequestDto;
import com.example.hotel_reservation.dto.category.CategoryResponseDto;

import java.util.List;
import java.util.Optional;

public interface ICategoryService {

    CategoryResponseDto createCategory(CategoryRequestDto categoryRequestDto);

    List<CategoryResponseDto> getAllCategories();

    Optional<CategoryResponseDto> findCategoryById(Long id);

    Optional<CategoryResponseDto> findCategoryByType(String categoryType);

    boolean existsByCategoryType(String categoryType);
}
