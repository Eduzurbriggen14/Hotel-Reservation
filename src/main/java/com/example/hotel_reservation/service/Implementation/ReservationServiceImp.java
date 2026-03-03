package com.example.hotel_reservation.service.Implementation;

import com.example.hotel_reservation.dto.Reservation.CreateReservationRequestDto;
import com.example.hotel_reservation.dto.Reservation.ReservationResponseDto;
import com.example.hotel_reservation.dto.Reservation.UpdateReservationDto;
import com.example.hotel_reservation.entity.Reservation;
import com.example.hotel_reservation.entity.ReservationStatus;
import com.example.hotel_reservation.entity.Room;
import com.example.hotel_reservation.entity.User;
import com.example.hotel_reservation.exception.BadRequestException;
import com.example.hotel_reservation.exception.ResourceNotFoundException;
import com.example.hotel_reservation.mapper.ReservationsMappers;
import com.example.hotel_reservation.repository.IReservationRepository;
import com.example.hotel_reservation.repository.IRoomRepository;
import com.example.hotel_reservation.repository.IUserRepository;
import com.example.hotel_reservation.service.IReservationService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
public class ReservationServiceImp implements IReservationService {

    private final IReservationRepository reservationRepository;
    private final IUserRepository userRepository;
    private final IRoomRepository roomRepository;


    public ReservationServiceImp(IReservationRepository reservationRepository,
                                 IUserRepository userRepository,
                                 IRoomRepository roomRepository) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
    }

    @Override
    public ReservationResponseDto createReservation(CreateReservationRequestDto createReservationRequestDto) {
        if (createReservationRequestDto == null) {
            throw new BadRequestException("Reservation data cannot be null");
        }

        // Obtener usuario completo desde BD
        if (createReservationRequestDto.userId() == null) {
            throw new BadRequestException("User ID cannot be null");
        }
        User user = userRepository.findById(createReservationRequestDto.userId())
                .orElseThrow(() -> new ResourceNotFoundException("User with ID " + createReservationRequestDto.userId() + " not found"));

        // Obtener habitación completa desde BD
        if (createReservationRequestDto.roomNumber() == null || createReservationRequestDto.roomNumber().isBlank()) {
            throw new BadRequestException("Room number cannot be null or empty");
        }
        Room room = roomRepository.findByRoomNumber(createReservationRequestDto.roomNumber())
                .orElseThrow(() -> new ResourceNotFoundException("Room with number '" + createReservationRequestDto.roomNumber() + "' not found"));

        // Validar fechas
        if (createReservationRequestDto.checkInDate() == null || createReservationRequestDto.checkOutDate() == null) {
            throw new BadRequestException("Check-in and check-out dates cannot be null");
        }
        LocalDate checkIn = convertToLocalDate(createReservationRequestDto.checkInDate());
        LocalDate checkOut = convertToLocalDate(createReservationRequestDto.checkOutDate());

        if (!checkOut.isAfter(checkIn)) {
            throw new BadRequestException("Check-out date must be after check-in date");
        }

        if (createReservationRequestDto.numberOfGuests() <= 0) {
            throw new BadRequestException("Number of guests must be greater than 0");
        }
        if (createReservationRequestDto.numberOfGuests() > 10) {
            throw new BadRequestException("Number of guests cannot exceed 10");
        }

        // Crear reserva con entidades completas desde BD
        Reservation reservation = ReservationsMappers.createReservationDtoToReservation(
                createReservationRequestDto, user, room);

        // Guardar la reserva - esto calculará automáticamente el totalAmount
        Reservation savedReservation = reservationRepository.save(reservation);


        return ReservationsMappers.reservationToReservationResponseDto(savedReservation);
    }

    @Override
    public List<ReservationResponseDto> listAllReservations() {
        List<Reservation> reservations = reservationRepository.findAll();
        return reservations.stream()
                .map(ReservationsMappers::reservationToReservationResponseDto)
                .toList();
    }

    @Override
    public Optional<ReservationResponseDto> getReservationById(Long reservationId) {
        if (reservationId == null || reservationId <= 0) {
            throw new BadRequestException("Reservation ID must be a valid positive number");
        }
        return reservationRepository.findById(reservationId)
                .map(ReservationsMappers::reservationToReservationResponseDto);
    }

    @Override
    public ReservationResponseDto updateReservation(UpdateReservationDto updateReservationDto) {
        if (updateReservationDto == null) {
            throw new BadRequestException("Reservation update data cannot be null");
        }

        if (updateReservationDto.reservationId() == null || updateReservationDto.reservationId() <= 0) {
            throw new BadRequestException("Reservation ID must be a valid positive number");
        }

        Reservation reservation = reservationRepository.findById(updateReservationDto.reservationId())
                .orElseThrow(() -> new ResourceNotFoundException("Reservation with ID " + updateReservationDto.reservationId() + " not found"));

        // Actualizar solo si se proporcionan nuevos valores
        if (updateReservationDto.checkInDate() != null) {
            reservation.setCheckInDate(convertToLocalDate(updateReservationDto.checkInDate()));
        }
        if (updateReservationDto.checkOutDate() != null) {
            reservation.setCheckOutDate(convertToLocalDate(updateReservationDto.checkOutDate()));
        }
        if (updateReservationDto.numberOfGuests() > 0) {
            if (updateReservationDto.numberOfGuests() > 10) {
                throw new BadRequestException("Number of guests cannot exceed 10");
            }
            reservation.setNumberOfGuests(updateReservationDto.numberOfGuests());
        }

        if (!reservation.checkCheckOut()) {
            throw new BadRequestException("Check-out date must be after check-in date");
        }

        Reservation updatedReservation = reservationRepository.save(reservation);
        return ReservationsMappers.reservationToReservationResponseDto(updatedReservation);
    }

    @Override
    public ReservationResponseDto cancelReservation(Long reservationId) {
        if (reservationId == null || reservationId <= 0) {
            throw new BadRequestException("Reservation ID must be a valid positive number");
        }

        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation with ID " + reservationId + " not found"));

        reservation.setReservationStatus(ReservationStatus.CANCELLED);
        Reservation cancelledReservation = reservationRepository.save(reservation);

        return ReservationsMappers.reservationToReservationResponseDto(cancelledReservation);
    }


    private LocalDate convertToLocalDate(Date date) {
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
}
