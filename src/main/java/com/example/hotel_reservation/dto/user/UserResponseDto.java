package com.example.hotel_reservation.dto.user;


public record UserResponseDto(Long userId,
                              String userEmail,
                              String name,
                              String lastName,
                              String userRol) {
}
