package com.example.hotel_reservation.dto.Reservation;

import com.example.hotel_reservation.dto.room.RoomResponseDto;
import com.example.hotel_reservation.dto.user.UserReservationDto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ReservationResponseDto(
        Long reservationId,
        UserReservationDto user,
        RoomResponseDto room,
        int numberOfGuests,
        LocalDate checkInDate,
        LocalDate checkOutDate,
        BigDecimal totalAmount
) {
}


