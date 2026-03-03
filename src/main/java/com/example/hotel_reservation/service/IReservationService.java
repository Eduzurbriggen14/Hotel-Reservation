package com.example.hotel_reservation.service;

import com.example.hotel_reservation.dto.Reservation.CreateReservationRequestDto;
import com.example.hotel_reservation.dto.Reservation.ReservationResponseDto;
import com.example.hotel_reservation.dto.Reservation.UpdateReservationDto;

import java.util.List;
import java.util.Optional;

public interface IReservationService {

    public ReservationResponseDto createReservation(CreateReservationRequestDto createReservationRequestDto);

    public List<ReservationResponseDto> listAllReservations();

    public Optional<ReservationResponseDto> getReservationById(Long reservationId);

    public ReservationResponseDto updateReservation(UpdateReservationDto updateReservationDto);

    public ReservationResponseDto cancelReservation(Long reservationId);
}
