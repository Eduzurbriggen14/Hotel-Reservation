package com.example.hotel_reservation.dto.room;

import com.example.hotel_reservation.dto.category.CategoryResponseDto;
import com.example.hotel_reservation.entity.RoomState;

public record RoomResponseDto(Long roomId,
                              String roomNumber,
                              RoomState roomStatus,
                              CategoryResponseDto category) {
}
