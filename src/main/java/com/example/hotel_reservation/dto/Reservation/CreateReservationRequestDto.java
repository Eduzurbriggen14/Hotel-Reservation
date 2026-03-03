package com.example.hotel_reservation.dto.Reservation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.Date;

public record CreateReservationRequestDto(@NotNull Long userId,
                                          @NotBlank String roomNumber,
                                          @NotNull @Positive int numberOfGuests,
                                          @NotNull Date checkInDate,
                                          @NotNull Date checkOutDate)  {
}


