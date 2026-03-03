package com.example.hotel_reservation.service;

import com.example.hotel_reservation.dto.room.RoomResponseDto;
import com.example.hotel_reservation.dto.room.RoomRequestDto;
import com.example.hotel_reservation.dto.room.UpdateRoomRequestDto;
import jakarta.validation.constraints.NotBlank;

import java.util.List;
import java.util.Optional;

public interface IRoomService {

    RoomResponseDto createRoom(RoomRequestDto roomRequestDto);

    List<RoomResponseDto> getAllRooms();

    List<RoomResponseDto> getRoomsByCategoryType(String catType);

    List<RoomResponseDto> getRoomsByState(String roomState);

    RoomResponseDto updateRoom(UpdateRoomRequestDto updateRoomRequestDto);

    boolean existsByRoomNumber(String roomNumber);

    Optional<RoomResponseDto> getRoomByRoomNumber(@NotBlank String s);
}
