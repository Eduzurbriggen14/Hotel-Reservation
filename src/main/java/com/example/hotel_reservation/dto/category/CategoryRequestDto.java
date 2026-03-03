package com.example.hotel_reservation.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record CategoryRequestDto(@NotBlank String description,
                                 @NotBlank String categoryType,
                                 @Positive int maxOccupancy,
                                 @Positive BigDecimal pricePerNight) {
}
