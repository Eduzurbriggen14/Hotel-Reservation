package com.example.hotel_reservation.controllers;


import com.example.hotel_reservation.dto.category.CategoryRequestDto;
import com.example.hotel_reservation.dto.category.CategoryResponseDto;
import com.example.hotel_reservation.entity.Category;
import com.example.hotel_reservation.mapper.category.CategoryMappers;
import com.example.hotel_reservation.service.ICategoryService;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    public final ICategoryService categoryService;
    public final CategoryMappers categoryMappers;

    public CategoryController(ICategoryService categoryService, CategoryMappers categoryMappers) {
        this.categoryService = categoryService;
        this.categoryMappers = categoryMappers;
    }

    @PostMapping
    public ResponseEntity<CategoryResponseDto> createCategory(@RequestBody CategoryRequestDto categoryRequestDto){
        if (categoryRequestDto == null){
            return ResponseEntity.noContent().build();
        }
        try {
            CategoryResponseDto categoryResponseDto = categoryService.createCategory(categoryRequestDto);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{categoryId}")
                    .buildAndExpand(categoryResponseDto.categoryId())
                    .toUri();

            return ResponseEntity.created(location).body(categoryResponseDto);
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryResponseDto> getCategoryById(@PathVariable Long categoryId){
        Category category = categoryService.findCategoryById(categoryId);
        if (category == null){
            return ResponseEntity.notFound().build();
        }
        CategoryResponseDto categoryResponseDto = categoryMappers.categoryToCategoryResponseDto(category);

        return ResponseEntity.ok(categoryResponseDto);
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponseDto>> getAllCategories() {
        List<CategoryResponseDto> categoryResponseDtoList = categoryService.getAllCategories();
        if (categoryResponseDtoList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(categoryResponseDtoList);
    }

    @PutMapping
    public ResponseEntity<CategoryResponseDto> updateCategory(@Valid @RequestBody CategoryRequestDto categoryRequestDto){
        if (categoryRequestDto == null){
            return ResponseEntity.noContent().build();
        }
        try {
            CategoryResponseDto categoryResponseDto = categoryService.createCategory(categoryRequestDto);
            return ResponseEntity.ok(categoryResponseDto);
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().build();
        }
    }

}
