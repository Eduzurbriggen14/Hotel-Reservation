package com.example.hotel_reservation.dto.room;

import jakarta.validation.constraints.NotNull;

public record UpdateRoomRequestDto(@NotNull Long roomId,
                                   String roomNumber,
                                   String roomStatus,
                                   Long categoryId,
                                   String categoryType) {
}
