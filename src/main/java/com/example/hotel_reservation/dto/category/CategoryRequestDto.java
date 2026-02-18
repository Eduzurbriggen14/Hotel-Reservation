package com.example.hotel_reservation.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record CategoryRequestDto(@NotBlank String categoryName,
                                 @NotBlank String description,
                                 @NotBlank String categoryType,
                                 @NotBlank @Positive int maxOccupancy,
                                 @NotBlank @Positive BigDecimal pricePerNight) {
}
