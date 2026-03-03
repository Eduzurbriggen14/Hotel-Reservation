package com.example.hotel_reservation.service.Implementation;

import com.example.hotel_reservation.dto.user.UserRequestDto;
import com.example.hotel_reservation.dto.user.UserResponseDto;
import com.example.hotel_reservation.dto.user.UserUpdateRequestDto;
import com.example.hotel_reservation.entity.User;
import com.example.hotel_reservation.entity.UserRol;
import com.example.hotel_reservation.entity.UserStatus;
import com.example.hotel_reservation.exception.BadRequestException;
import com.example.hotel_reservation.exception.ResourceNotFoundException;
import com.example.hotel_reservation.mapper.UserMappers;
import com.example.hotel_reservation.repository.IUserRepository;
import com.example.hotel_reservation.service.IUserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImp implements IUserService {

    private final IUserRepository userRepository;
    private final UserMappers userMappers;

    public UserServiceImp(IUserRepository iUserRepository, UserMappers userMappers) {
        this.userRepository = iUserRepository;
        this.userMappers = userMappers;
    }

    @Override
    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        if (userRequestDto == null) {
            throw new BadRequestException("User data cannot be null");
        }

        if (userRepository.existsByUserEmail(userRequestDto.userEmail())) {
            throw new BadRequestException("Email '" + userRequestDto.userEmail() + "' is already in use");
        }

        if (userRepository.existsByUserName(userRequestDto.userName())) {
            throw new BadRequestException("Username '" + userRequestDto.userName() + "' is already in use");
        }

        User user = userMappers.userRequestDtoToUser(userRequestDto);
        User savedUser = userRepository.save(user);

        return userMappers.userToResponseDto(savedUser);
    }

    @Override
    public List<UserResponseDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(userMappers::userToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponseDto getUserById(Long userId) {
        if (userId == null || userId <= 0) {
            throw new BadRequestException("User ID must be a valid positive number");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID " + userId + " not found"));

        return userMappers.userToResponseDto(user);
    }

    @Override
    public UserResponseDto getUserByEmail(String userEmail) {
        if (userEmail == null || userEmail.isBlank()) {
            throw new BadRequestException("Email cannot be null or empty");
        }

        if (!userRepository.existsByUserEmail(userEmail)) {
            throw new ResourceNotFoundException("User with email '" + userEmail + "' not found");
        }

        User user = userRepository.findByUserEmailIgnoreCase(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User with email '" + userEmail + "' not found"));

        return userMappers.userToResponseDto(user);
    }

    @Override
    public List<UserResponseDto> getUsersByRol(String userRol) {
        if (userRol == null || userRol.isBlank()) {
            throw new BadRequestException("User role cannot be null or empty");
        }

        try {
            UserRol role = UserRol.valueOf(userRol.toUpperCase());
            List<User> usersByRol = userRepository.findByUserRol(role);

            if (usersByRol.isEmpty()) {
                throw new ResourceNotFoundException("No users found with role '" + userRol + "'");
            }

            return usersByRol.stream()
                    .map(userMappers::userToResponseDto)
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Invalid user role: '" + userRol + "'. Valid roles are: ADMIN, EMPLOYEE, CLIENT");
        }
    }

    @Override
    public UserResponseDto updateUser(UserUpdateRequestDto userUpdateRequestDto) {
        if (userUpdateRequestDto == null) {
            throw new BadRequestException("User update data cannot be null");
        }

        Long userId = userUpdateRequestDto.userId();
        if (userId == null || userId <= 0) {
            throw new BadRequestException("User ID must be a valid positive number");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID " + userId + " not found"));

        // Actualizar solo si se proporcionan nuevos valores (partial update)
        if (userUpdateRequestDto.userPassword() != null && !userUpdateRequestDto.userPassword().isBlank()) {
            if (userUpdateRequestDto.userPassword().length() < 4) {
                throw new BadRequestException("Password must be at least 4 characters long");
            }
            user.setUserPassword(userUpdateRequestDto.userPassword());
        }

        if (userUpdateRequestDto.name() != null && !userUpdateRequestDto.name().isBlank()) {
            user.setName(userUpdateRequestDto.name());
        }

        if (userUpdateRequestDto.lastName() != null && !userUpdateRequestDto.lastName().isBlank()) {
            user.setLastName(userUpdateRequestDto.lastName());
        }

        User savedUser = userRepository.save(user);
        return userMappers.userToResponseDto(savedUser);
    }

    @Override
    public void deleteUser(Long userId) {
        if (userId == null || userId <= 0) {
            throw new BadRequestException("User ID must be a valid positive number");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID " + userId + " not found"));

        user.setUserStatus(UserStatus.INACTIVE);
        userRepository.save(user);
    }
}


