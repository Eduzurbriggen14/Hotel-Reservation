package com.example.hotel_reservation.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserRequestDto(@NotBlank String userName,
                             @NotBlank @Email String userEmail,
                             @NotBlank String password,
                             @NotBlank String name,
                             @NotBlank String lastName,
                             @NotBlank String userRol) {
}
