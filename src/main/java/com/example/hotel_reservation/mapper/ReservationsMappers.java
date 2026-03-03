package com.example.hotel_reservation.mapper;

import com.example.hotel_reservation.dto.Reservation.CreateReservationRequestDto;
import com.example.hotel_reservation.dto.Reservation.ReservationResponseDto;
import com.example.hotel_reservation.entity.Reservation;
import com.example.hotel_reservation.entity.ReservationStatus;
import com.example.hotel_reservation.entity.Room;
import com.example.hotel_reservation.entity.User;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;

@Component
public class ReservationsMappers {

    public static ReservationResponseDto reservationToReservationResponseDto(Reservation reservation) {
        if (reservation == null) return null;
        return new ReservationResponseDto(
                reservation.getReservationId(),
                UserMappers.userToUserReservationDto(reservation.getUser()),
                RoomMappers.roomToRoomResponseDto(reservation.getRoom()),
                reservation.getNumberOfGuests(),
                reservation.getCheckInDate(),
                reservation.getCheckOutDate(),
                reservation.getTotalAmount()
        );
    }

    public static Reservation createReservationDtoToReservation(CreateReservationRequestDto dto, User user, Room room) {
        if (dto == null) return null;

        Reservation reservation = new Reservation();
        reservation.setCheckInDate(convertToLocalDate(dto.checkInDate()));
        reservation.setCheckOutDate(convertToLocalDate(dto.checkOutDate()));
        reservation.setNumberOfGuests(dto.numberOfGuests());
        reservation.setReservationStatus(ReservationStatus.PENDING);
        reservation.setUser(user);
        reservation.setRoom(room);

        return reservation;
    }

    private static LocalDate convertToLocalDate(java.util.Date date) {
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
}


