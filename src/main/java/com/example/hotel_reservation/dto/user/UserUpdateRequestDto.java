package com.example.hotel_reservation.dto.user;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record UserUpdateRequestDto(@NotNull @Positive Long userId,
                                   String userPassword,
                                   String name,
                                   String lastName) {
}
