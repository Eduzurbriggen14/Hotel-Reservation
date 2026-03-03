package com.example.hotel_reservation.dto.user;

public record UserReservationDto(
        Long userId,
        String userEmail,
        String name,
        String lastName
) {
}

