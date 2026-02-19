package com.example.hotel_reservation.mapper.category;

import com.example.hotel_reservation.dto.category.CategoryRequestDto;
import com.example.hotel_reservation.dto.category.CategoryResponseDto;
import com.example.hotel_reservation.entity.Category;
import com.example.hotel_reservation.entity.CategoryType;
import org.springframework.stereotype.Component;

@Component
public class CategoryMappers {

    public Category categoryResponseDtotoCategory(CategoryResponseDto categoryResponseDto){
        if (categoryResponseDto == null) return null;
        CategoryType type = null;
        if (categoryResponseDto.categoryType() != null) {
            try{
                type = CategoryType.valueOf(categoryResponseDto.categoryType().toUpperCase());
            } catch (IllegalArgumentException e){
                // dejar type null; quien llame debe validar
            }
        }
        return new Category(categoryResponseDto.categoryId(),
                categoryResponseDto.categoryName(),
                categoryResponseDto.description(),
                categoryResponseDto.pricePerNight(),
                categoryResponseDto.maxOccupancy(),
                null,
                type);
    }

    public CategoryResponseDto categoryToCategoryResponseDto(Category category){
        if (category == null) return null;
        return new CategoryResponseDto(category.getCategoryId(),
                    category.getCategoryName(),
                    category.getDescription(),
                    category.getCategoryType() != null ? category.getCategoryType().name() : null,
                    category.getMaxOccupancy(),
                    category.getPricePerNight());
    }

    public Category categoryRequestDtoToCategory(CategoryRequestDto categoryRequestDto){
        if (categoryRequestDto == null) return null;
        CategoryType type = null;
        if (categoryRequestDto.categoryType() != null){
            try{
                type = CategoryType.valueOf(categoryRequestDto.categoryType().toUpperCase());
            } catch (IllegalArgumentException e){
                throw new IllegalArgumentException("categoryType invalid: " + categoryRequestDto.categoryType());
            }
        }
        return new Category(null,
                categoryRequestDto.categoryName(),
                categoryRequestDto.description(),
                categoryRequestDto.pricePerNight(),
                categoryRequestDto.maxOccupancy(),
                null,
                type);
    }
}
