package com.example.hotel_reservation.service.Implementation;

import com.example.hotel_reservation.dto.room.RoomResponseDto;
import com.example.hotel_reservation.dto.roomService.CreateRoomServiceDto;
import com.example.hotel_reservation.dto.roomService.RoomServiceResponseDto;
import com.example.hotel_reservation.dto.user.UserResponseDto;
import com.example.hotel_reservation.entity.*;
import com.example.hotel_reservation.mapper.RoomMappers;
import com.example.hotel_reservation.mapper.RoomServiceMappers;
import com.example.hotel_reservation.mapper.UserMappers;
import com.example.hotel_reservation.repository.IRoomServiceRepository;
import com.example.hotel_reservation.service.IRoomService;
import com.example.hotel_reservation.service.IRoomServiceService;
import com.example.hotel_reservation.service.IUserService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class RoomServiceServiceImp implements IRoomServiceService {

    private final IRoomService roomService;
    private final IUserService userService;
    private final IRoomServiceRepository roomServiceRepository;

    public RoomServiceServiceImp(IRoomService roomService, IUserService userService, IRoomServiceRepository roomServiceRepository) {
        this.roomService = roomService;
        this.userService = userService;
        this.roomServiceRepository = roomServiceRepository;
    }


    @Override
    public RoomServiceResponseDto createRoomService(CreateRoomServiceDto createRoomServiceDto) {
        UserResponseDto employee = userService.getUserById(createRoomServiceDto.userId());
        if (employee == null){
           throw new IllegalArgumentException("Employee with ID " + createRoomServiceDto.userId() + " not found.");
        }

        User userEmployee = UserMappers.UserResponseDtoToUser(employee);
        if (!roomService.existsByRoomNumber(createRoomServiceDto.roomNumber())){
            throw new IllegalArgumentException("Room with number " + createRoomServiceDto.roomNumber() + " not found.");
        }
        Optional<RoomResponseDto> roomResponseDto = roomService.getRoomByRoomNumber(createRoomServiceDto.roomNumber());
        RoomResponseDto roomDto = roomResponseDto.get();

        Room room = RoomMappers.roomResponseDtoToRoom(roomDto);
        ServiceType serviceType = ServiceType.valueOf(createRoomServiceDto.servicetype().toUpperCase());
        ServiceStatus serviceStatus = ServiceStatus.valueOf(createRoomServiceDto.servicesStatus().toUpperCase());

        LocalDate serviceDate = LocalDate.now();

        RoomService newRoomService = new RoomService(serviceDate,
                createRoomServiceDto.notes(),
                serviceType,
                serviceStatus,
                userEmployee,
                room);
        RoomService roomServiceSave = roomServiceRepository.save(newRoomService);


        return RoomServiceMappers.roomServiceToRoomServiceResponseDto(roomServiceSave);
    }

    @Override
    public List<RoomServiceResponseDto> getAllRoomServices() {
        List<RoomService> roomServiceList = roomServiceRepository.findAll();
        if (roomServiceList.isEmpty()){
            throw new IllegalArgumentException("No room services found.");
        }
        return roomServiceList.stream()
                .map(RoomServiceMappers::roomServiceToRoomServiceResponseDto)
                .toList();
    }

    @Override
    public RoomServiceResponseDto getRoomServiceById(Long roomServiceId) {
        RoomService roomService = roomServiceRepository.findById(roomServiceId)
                .orElseThrow(() -> new IllegalArgumentException("Room service with ID " + roomServiceId + " not found."));

        return RoomServiceMappers.roomServiceToRoomServiceResponseDto(roomService);
    }
}
