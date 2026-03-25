package com.example.hotel_reservation.service;

import com.example.hotel_reservation.dto.roomService.CreateRoomServiceDto;
import com.example.hotel_reservation.dto.roomService.RoomServiceResponseDto;

import java.util.List;

public interface IRoomServiceService {

    public RoomServiceResponseDto createRoomService(CreateRoomServiceDto createRoomServiceDto);

    List<RoomServiceResponseDto> getAllRoomServices();

    public RoomServiceResponseDto getRoomServiceById(Long roomServiceId);


}
