package com.example.hotel_reservation.mapper;

import com.example.hotel_reservation.dto.roomService.RoomServiceResponseDto;
import com.example.hotel_reservation.entity.*;

public class RoomServiceMappers {

    public static RoomServiceResponseDto roomServiceToRoomServiceResponseDto(RoomService roomService){
        if (roomService == null) return null;

        Room room = roomService.getRoom();
        String roomNumber = room != null ? room.getRoomNumber() : null;

        User employee = roomService.getEmployee();
        String name = employee != null ? employee.getName() : null;
        String lastName = employee != null ? employee.getLastName() : null;

        ServiceType serviceType = roomService.getServiceType();
        String serviceTypeStr = serviceType.name();

        ServiceStatus serviceStatus = roomService.getServiceStatus();
        String serviceStatusStr = serviceStatus.name();

        return new RoomServiceResponseDto(name, lastName, roomNumber, serviceTypeStr, roomService.getServiceDate());
    }
}
