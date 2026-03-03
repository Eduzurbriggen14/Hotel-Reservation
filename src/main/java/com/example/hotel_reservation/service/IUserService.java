package com.example.hotel_reservation.service;

import com.example.hotel_reservation.dto.user.UserRequestDto;
import com.example.hotel_reservation.dto.user.UserResponseDto;
import com.example.hotel_reservation.dto.user.UserUpdateRequestDto;

import java.util.List;

public interface IUserService {

    public UserResponseDto createUser(UserRequestDto userRequestDto);

    public List<UserResponseDto> getAllUsers();

    public UserResponseDto getUserById(Long userId);

    public UserResponseDto getUserByEmail(String userEmail);

    public List<UserResponseDto> getUsersByRol(String userRol);

    public UserResponseDto updateUser(UserUpdateRequestDto userUpdateRequestDto);

    public void deleteUser(Long userId);
}

