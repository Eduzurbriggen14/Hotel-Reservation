package com.example.hotel_reservation.mapper;

import com.example.hotel_reservation.dto.category.CategoryResponseDto;
import com.example.hotel_reservation.dto.room.RoomRequestDto;
import com.example.hotel_reservation.dto.room.RoomResponseDto;
import com.example.hotel_reservation.entity.Category;
import com.example.hotel_reservation.entity.Room;
import com.example.hotel_reservation.entity.RoomState;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class RoomMappers {

    public static Room roomRequestDtoToRoom(RoomRequestDto dto, Category category) {
        if (dto == null) return null;
        if (category == null) return null;

        if (dto.roomNumber() == null || dto.roomNumber().isBlank()) return null;
        if (dto.roomState() == null || dto.roomState().isBlank()) return null;

        RoomState state;
        try {
            state = RoomState.valueOf(dto.roomState().toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException ex) {
            return null;
        }

        Room room = new Room();
        room.setRoomNumber(dto.roomNumber());
        room.setRoomStatus(state);
        room.setCategory(category);
        return room;
    }


    public static RoomResponseDto roomToRoomResponseDto(Room room) {
        if (room == null) return null;

        Category category = room.getCategory();
        CategoryResponseDto categoryDto = null;
        if (category != null) {
            categoryDto = CategoryMappers.categoryToCategoryResponseDto(category);
        }

        return new RoomResponseDto(
                room.getRoomId(),
                room.getRoomNumber(),
                room.getRoomStatus(),
                categoryDto
        );
    }

    public static Room roomResponseDtoToRoom(RoomResponseDto dto) {
        if (dto == null) return null;

        Room room = new Room();
        room.setRoomId(dto.roomId());
        room.setRoomNumber(dto.roomNumber());
        room.setRoomStatus(dto.roomStatus());

        CategoryResponseDto categoryDto = dto.category();
        if (categoryDto != null) {
            Category category = CategoryMappers.categoryResponseDtoToCategory(categoryDto);
            room.setCategory(category);
        }

        return room;
    }
}
