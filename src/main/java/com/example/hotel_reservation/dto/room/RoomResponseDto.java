package com.example.hotel_reservation.dto.room;

import com.example.hotel_reservation.entity.Category;

public record RoomResponseDto(Long roomId,
                              String roombumber,
                              String roomState,
                              Category category) {
}
