package com.example.hotel_reservation.service.Implementation;

import com.example.hotel_reservation.dto.user.UserRequestDto;
import com.example.hotel_reservation.dto.user.UserResponseDto;
import com.example.hotel_reservation.dto.user.UserUpdateRequestDto;
import com.example.hotel_reservation.entity.User;
import com.example.hotel_reservation.entity.UserRol;
import com.example.hotel_reservation.mapper.user.UserMappers;
import com.example.hotel_reservation.repository.IUserRepository;
import com.example.hotel_reservation.service.IUserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImp implements IUserService {

    private  final IUserRepository iUserRepository;
    private final UserMappers userMappers;

    public UserServiceImp(IUserRepository iUserRepository, UserMappers userMappers) {
        this.iUserRepository = iUserRepository;
        this.userMappers = userMappers;
    }

    @Override
    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        User user = userMappers.userRequestDtoToUser(userRequestDto);
        User save = iUserRepository.save(user);

        return userMappers.userToResponseDto(save);
    }

    @Override
    public List<UserResponseDto> getAllUsers() {
        List<User> users = iUserRepository.findAll();

        return users.stream().map(userMappers::userToResponseDto).collect(Collectors.toList());
    }

    @Override
    public UserResponseDto getUserById(Long userId) {
        User user = iUserRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        return userMappers.userToResponseDto(user);
    }

    @Override
    public UserResponseDto getUserByEmail(String userEmail) {

        if (!iUserRepository.existsByUserEmail(userEmail)) {
            throw new RuntimeException("User not found");
        }

        User user = iUserRepository.findByUserEmailIgnoreCase(userEmail).orElseThrow(() -> new RuntimeException("User not found"));
        UserResponseDto userResponseDto = userMappers.userToResponseDto(user);
        return userResponseDto;
    }

    @Override
    public List<UserResponseDto> getUsersByRol(String userRol) {
        List<User> usersByRol = iUserRepository.findByUserRol(UserRol.valueOf(userRol.toUpperCase()));

        return usersByRol.stream().map(userMappers::userToResponseDto).collect(Collectors.toList());

    }

    @Override
    public UserResponseDto updateUser(UserUpdateRequestDto userUpdateRequestDto) {
        Long id = userUpdateRequestDto.userId();
        User user = iUserRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

        // update fields if present (allow partial updates)
        if (userUpdateRequestDto.userPassword() != null && !userUpdateRequestDto.userPassword().isBlank()) {
            user.setUserPassword(userUpdateRequestDto.userPassword());
        }
        if (userUpdateRequestDto.name() != null && !userUpdateRequestDto.name().isBlank()) {
            user.setName(userUpdateRequestDto.name());
        }
        if (userUpdateRequestDto.lastName() != null && !userUpdateRequestDto.lastName().isBlank()) {
            user.setLastName(userUpdateRequestDto.lastName());
        }

        User saved = iUserRepository.save(user);
        return userMappers.userToResponseDto(saved);
    }
}
