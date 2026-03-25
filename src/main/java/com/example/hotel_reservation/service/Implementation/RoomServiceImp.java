package com.example.hotel_reservation.service.Implementation;

import com.example.hotel_reservation.dto.room.RoomRequestDto;
import com.example.hotel_reservation.dto.room.RoomResponseDto;
import com.example.hotel_reservation.dto.room.UpdateRoomRequestDto;
import com.example.hotel_reservation.entity.Category;
import com.example.hotel_reservation.entity.CategoryType;
import com.example.hotel_reservation.entity.Room;
import com.example.hotel_reservation.entity.RoomState;
import com.example.hotel_reservation.exception.BadRequestException;
import com.example.hotel_reservation.exception.ResourceNotFoundException;
import com.example.hotel_reservation.mapper.RoomMappers;
import com.example.hotel_reservation.repository.ICategoryRepository;
import com.example.hotel_reservation.repository.IRoomRepository;
import com.example.hotel_reservation.service.IRoomService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoomServiceImp implements IRoomService {

    private final IRoomRepository roomRepository;
    private final ICategoryRepository categoryRepository;

    public RoomServiceImp(IRoomRepository roomRepository, ICategoryRepository categoryRepository) {
        this.roomRepository = roomRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public RoomResponseDto createRoom(RoomRequestDto roomRequestDto) {
        if (roomRequestDto == null) throw new BadRequestException("roomRequestDto cannot be null");
        if (roomRequestDto.roomNumber() == null || roomRequestDto.roomNumber().isBlank())
            throw new BadRequestException("roomNumber is required");

        if (roomRepository.findByRoomNumber(roomRequestDto.roomNumber()).isPresent())
            throw new BadRequestException("Room with number '" + roomRequestDto.roomNumber() + "' already exists");

        if (roomRequestDto.categoryType() == null || roomRequestDto.categoryType().isBlank()) {
            throw new BadRequestException("categoryType is required");
        }

        Category category;
        try {
            CategoryType ct = CategoryType.valueOf(roomRequestDto.categoryType().toUpperCase(Locale.ROOT));
            category = categoryRepository.findByCategoryType(ct)
                    .orElseThrow(() -> new BadRequestException("Category with type '" + ct + "' not found"));
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Invalid categoryType: " + roomRequestDto.categoryType(), e);
        }

        Room room = RoomMappers.roomRequestDtoToRoom(roomRequestDto, category);
        Room saved = roomRepository.save(room);

        category.addRoomToListRoom(saved);

        return RoomMappers.roomToRoomResponseDto(saved);
    }

    @Override
    public List<RoomResponseDto> getAllRooms() {
        List<Room> rooms = roomRepository.findAll();
        return rooms.stream()
                .map(RoomMappers::roomToRoomResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<RoomResponseDto> getRoomsByCategoryType(String catType){
        if (catType == null || catType.isBlank()) return Collections.emptyList();
        try{
            CategoryType ct = CategoryType.valueOf(catType.toUpperCase(Locale.ROOT));
            if (!categoryRepository.existsByCategoryType(ct)){
                return Collections.emptyList();
            }
            List<Room> roomsCategoryTypeList = roomRepository.findByCategoryCategoryType(ct);
            return roomsCategoryTypeList.stream()
                    .map(RoomMappers::roomToRoomResponseDto)
                    .collect(Collectors.toList());
        }catch(IllegalArgumentException e){
            return Collections.emptyList();
        }
    }

    @Override
    public List<RoomResponseDto> getRoomsByState(String roomState){
        if (roomState ==null || roomState.isBlank()) return Collections.emptyList();

        try{
            RoomState state = RoomState.valueOf(roomState.toUpperCase(Locale.ROOT));
            List<Room> roomsStateList = roomRepository.findByRoomStatus(state);
            return roomsStateList.stream()
                    .map(RoomMappers::roomToRoomResponseDto)
                    .collect(Collectors.toList());

        }catch(IllegalArgumentException e){
            return Collections.emptyList();
        }
    }

    @Override
    public RoomResponseDto updateRoom(UpdateRoomRequestDto updateRoomRequestDto){
        if (updateRoomRequestDto == null || updateRoomRequestDto.roomId() == null) {
            throw new BadRequestException("Room update data and room ID are required");
        }

        Room room = roomRepository.findById(updateRoomRequestDto.roomId())
                .orElseThrow(() -> new ResourceNotFoundException("Room with ID " + updateRoomRequestDto.roomId() + " not found"));

        // Actualizar número de habitación
        if (updateRoomRequestDto.roomNumber() != null && !updateRoomRequestDto.roomNumber().isBlank()){
            if (roomRepository.findByRoomNumber(updateRoomRequestDto.roomNumber()).isPresent()) {
                throw new BadRequestException("Room number '" + updateRoomRequestDto.roomNumber() + "' is already in use");
            }
            room.setRoomNumber(updateRoomRequestDto.roomNumber());
        }

        // Actualizar estado
        if (updateRoomRequestDto.roomStatus() != null && !updateRoomRequestDto.roomStatus().isBlank()){
            try {
                RoomState rs = RoomState.valueOf(updateRoomRequestDto.roomStatus().toUpperCase(Locale.ROOT));
                room.setRoomStatus(rs);
            } catch (IllegalArgumentException ex){
                throw new BadRequestException("Invalid room status: '" + updateRoomRequestDto.roomStatus() + "'", ex);
            }
        }

        // Actualizar categoría
        if (updateRoomRequestDto.categoryId() != null) {
            Category category = categoryRepository.findById(updateRoomRequestDto.categoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category with ID " + updateRoomRequestDto.categoryId() + " not found"));
            room.setCategory(category);
        } else if (updateRoomRequestDto.categoryType() != null && !updateRoomRequestDto.categoryType().isBlank()){
            try {
                CategoryType ct = CategoryType.valueOf(updateRoomRequestDto.categoryType().toUpperCase(Locale.ROOT));
                Category category = categoryRepository.findByCategoryType(ct)
                        .orElseThrow(() -> new ResourceNotFoundException("Category with type '" + ct + "' not found"));
                room.setCategory(category);
            } catch (IllegalArgumentException ex) {
                throw new BadRequestException("Invalid category type: '" + updateRoomRequestDto.categoryType() + "'", ex);
            }
        }

        Room updatedRoom = roomRepository.save(room);
        return RoomMappers.roomToRoomResponseDto(updatedRoom);
    }

    @Override
    public boolean existsByRoomNumber(String roomNumber) {
        Optional<Room> room = roomRepository.findByRoomNumber(roomNumber);
        return room.isPresent();
    }

    @Override
    public Optional<RoomResponseDto> getRoomByRoomNumber(String roomNumber) {
        if (roomNumber == null || roomNumber.isBlank()) return Optional.empty();
        Optional<Room> room = roomRepository.findByRoomNumber(roomNumber);
        if (room.isPresent()){
            RoomResponseDto roomResponseDto = RoomMappers.roomToRoomResponseDto(room.get());
            return Optional.of(roomResponseDto);
        }
        return Optional.empty();
    }
}
