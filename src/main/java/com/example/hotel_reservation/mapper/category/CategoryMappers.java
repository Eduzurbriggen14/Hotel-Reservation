package com.example.hotel_reservation.mapper.category;

import com.example.hotel_reservation.dto.category.CategoryRequestDto;
import com.example.hotel_reservation.dto.category.CategoryResponseDto;
import com.example.hotel_reservation.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMappers {

    public Category categoryResponseDtotoCategory(CategoryResponseDto categoryResponseDto){
        return new Category(categoryResponseDto.categoryName(),
                categoryResponseDto.description(),
                categoryResponseDto.categoryType(),
                categoryResponseDto.maxOccupancy(),
                categoryResponseDto.pricePerNight());
    }

    public CategoryResponseDto categoryToCategoryResponseDto(Category category){
        return new CategoryResponseDto(category.getCategoryId(),
                    category.getCategoryName(),
                    category.getDescription(),
                    category.getCategoryType().name(),
                    category.getMaxOccupancy(),
                    category.getPricePerNight());
    }

    public Category categoryRequestDtoToCategory(CategoryRequestDto categoryRequestDto){
        return new Category(categoryRequestDto.categoryName(),
                categoryRequestDto.description(),
                categoryRequestDto.categoryType(),
                categoryRequestDto.maxOccupancy(),
                categoryRequestDto.pricePerNight());
    }
}
