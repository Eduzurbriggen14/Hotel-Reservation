package com.example.hotel_reservation.controllers;

import com.example.hotel_reservation.dto.user.UserRequestDto;
import com.example.hotel_reservation.dto.user.UserResponseDto;
import com.example.hotel_reservation.dto.user.UserUpdateRequestDto;
import com.example.hotel_reservation.service.IUserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final IUserService iUserService;

    public UserController(IUserService iUserService) {
        this.iUserService = iUserService;
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserRequestDto userRequestDto){
        if(userRequestDto == null){
            return ResponseEntity.badRequest().build();
        }
        UserResponseDto userResponseDto = iUserService.createUser(userRequestDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{userId}")
                .buildAndExpand(userResponseDto.userId())
                .toUri();
        return ResponseEntity.created(location).body(userResponseDto);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long userId){
        try{
            UserResponseDto userResponseDto = iUserService.getUserById(userId);
            return ResponseEntity.ok(userResponseDto);
        }catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers(){
        List<UserResponseDto> userResponseDtoList = iUserService.getAllUsers();
        if (userResponseDtoList.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(userResponseDtoList);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable Long userId, @Valid @RequestBody UserUpdateRequestDto userUpdateRequestDto){
        try{
            if (userUpdateRequestDto == null) return ResponseEntity.badRequest().build();
            if (!userId.equals(userUpdateRequestDto.userId())) return ResponseEntity.badRequest().build();
            UserResponseDto updated = iUserService.updateUser(userUpdateRequestDto);
            return ResponseEntity.ok(updated);
        }catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }

}
