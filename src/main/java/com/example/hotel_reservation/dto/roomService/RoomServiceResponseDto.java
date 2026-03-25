package com.example.hotel_reservation.dto.roomService;

import java.time.LocalDate;

public record RoomServiceResponseDto(String name,
                                     String lastName,
                                     String roomNumber,
                                     String serviceType,
                                     LocalDate serviceDate) {
}
