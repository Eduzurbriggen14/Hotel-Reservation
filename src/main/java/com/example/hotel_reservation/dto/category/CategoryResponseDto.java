package com.example.hotel_reservation.dto.category;

import java.math.BigDecimal;

public record CategoryResponseDto(Long categoryId,
                                  String description,
                                  String categoryType,
                                  int maxOccupancy,
                                  BigDecimal pricePerNight) {
}
