package com.example.hotel_reservation.dto.Reservation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.Date;

public record UpdateReservationDto(@NotNull Long reservationId,
                                   Date checkInDate,
                                   Date checkOutDate,
                                   @Positive int numberOfGuests) {
}
