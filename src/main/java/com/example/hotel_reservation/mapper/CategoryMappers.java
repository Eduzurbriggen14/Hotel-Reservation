package com.example.hotel_reservation.mapper;

import com.example.hotel_reservation.dto.category.CategoryRequestDto;
import com.example.hotel_reservation.dto.category.CategoryResponseDto;
import com.example.hotel_reservation.entity.Category;
import com.example.hotel_reservation.entity.CategoryType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class CategoryMappers {

    @SuppressWarnings("unused")
    public static Category categoryResponseDtoToCategory(CategoryResponseDto categoryResponseDto){
        if (categoryResponseDto == null) return null;
        CategoryType type = null;
        if (categoryResponseDto.categoryType() != null) {
            try{
                type = CategoryType.valueOf(categoryResponseDto.categoryType().toUpperCase());
            } catch (IllegalArgumentException e){
                // Valor de categoryType inválido: lo ignoramos aquí; quien llame debe validar.
            }
        }
        return new Category(
                categoryResponseDto.categoryId(),
                categoryResponseDto.description(),
                categoryResponseDto.pricePerNight(),
                categoryResponseDto.maxOccupancy(),
                new ArrayList<>(),
                type
        );
    }

    public static CategoryResponseDto categoryToCategoryResponseDto(Category category){
        if (category == null) return null;
        return new CategoryResponseDto(
                category.getCategoryId(),
                category.getDescription(),
                category.getCategoryType() != null ? category.getCategoryType().name() : null,
                category.getMaxOccupancy() != null ? category.getMaxOccupancy() : 0,
                category.getPricePerNight()
        );
    }

    @SuppressWarnings("unused")
    public static Category categoryRequestDtoToCategory(CategoryRequestDto categoryRequestDto){
        if (categoryRequestDto == null) return null;
        CategoryType type = null;
        if (categoryRequestDto.categoryType() != null){
            try{
                type = CategoryType.valueOf(categoryRequestDto.categoryType().toUpperCase());
            } catch (IllegalArgumentException e){
                throw new IllegalArgumentException("categoryType invalid: " + categoryRequestDto.categoryType());
            }
        }
        return new Category(
                null,
                categoryRequestDto.description(),
                categoryRequestDto.pricePerNight(),
                categoryRequestDto.maxOccupancy(),
                new ArrayList<>(),
                type
        );
    }
}
