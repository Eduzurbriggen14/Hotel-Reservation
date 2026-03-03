package com.example.hotel_reservation.controllers;

import com.example.hotel_reservation.dto.room.RoomRequestDto;
import com.example.hotel_reservation.dto.room.RoomResponseDto;
import com.example.hotel_reservation.dto.room.UpdateRoomRequestDto;
import com.example.hotel_reservation.exception.BadRequestException;
import com.example.hotel_reservation.exception.ResourceNotFoundException;
import com.example.hotel_reservation.service.IRoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    private final IRoomService roomService;

    public RoomController(IRoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping
    public ResponseEntity<RoomResponseDto> createRoom(@RequestBody RoomRequestDto dto){
        RoomResponseDto created = roomService.createRoom(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<List<RoomResponseDto>> getAll(){
        return ResponseEntity.ok(roomService.getAllRooms());
    }

    @GetMapping("/state/{state}")
    public ResponseEntity<List<RoomResponseDto>> getByState(@PathVariable String state){
        return ResponseEntity.ok(roomService.getRoomsByState(state));
    }

    @GetMapping("/category/{type}")
    public ResponseEntity<List<RoomResponseDto>> getByCategory(@PathVariable String type){
        return ResponseEntity.ok(roomService.getRoomsByCategoryType(type));
    }

    @PatchMapping
    public ResponseEntity<RoomResponseDto> updateRoom(@RequestBody UpdateRoomRequestDto dto){
        RoomResponseDto updated = roomService.updateRoom(dto);
        return ResponseEntity.ok(updated);
    }
}

