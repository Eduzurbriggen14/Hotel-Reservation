package com.example.hotel_reservation.dto.roomService;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateRoomServiceDto(@NotNull @Positive Long userId, //userId is the employee providing the service
                                   @NotBlank String roomNumber,
                                   String notes,
                                   @NotBlank String servicetype,
                                   @NotBlank String servicesStatus) {
}