package com.example.hotel_reservation.dto.user;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record UserUpdateRequestDto(@NotNull @Positive Long userId,
                                   @Size(min = 4, max = 15) String userPassword,
                                   String name,
                                   String lastName) {
}
