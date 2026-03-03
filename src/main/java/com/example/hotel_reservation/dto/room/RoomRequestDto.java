package com.example.hotel_reservation.dto.room;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RoomRequestDto(@NotNull Long roomId,
                             @NotBlank String roomNumber,
                             @NotBlank String roomState,
                             @NotBlank String categoryType) {
}
