package com.example.hotel_reservation.mapper;

import com.example.hotel_reservation.dto.user.UserRequestDto;
import com.example.hotel_reservation.dto.user.UserResponseDto;
import com.example.hotel_reservation.dto.user.UserReservationDto;
import com.example.hotel_reservation.entity.User;
import com.example.hotel_reservation.entity.UserRol;
import org.springframework.stereotype.Component;

@Component
public class UserMappers {

    public static User UserResponseDtoToUser(UserResponseDto employee) {
        if (employee == null) return null;

        User user = new User();
        user.setUserId(employee.userId());
        user.setUserEmail(employee.userEmail());
        user.setName(employee.name());
        user.setLastName(employee.lastName());

        if (employee.userRol() != null) {
            try {
                user.setUserRol(UserRol.valueOf(employee.userRol().toUpperCase()));
            } catch (IllegalArgumentException e) {
                user.setUserRol(UserRol.CLIENT);
            }
        } else {
            user.setUserRol(UserRol.CLIENT);
        }

        return user;
    }

    public UserRol stringToUserRol(String userRol){
        if (userRol == null) return UserRol.CLIENT;

        try{
            return UserRol.valueOf(userRol.toUpperCase());
        }catch(IllegalArgumentException e){
            return UserRol.CLIENT;
        }
    }

    public User userRequestDtoToUser(UserRequestDto userRequestDto){
        return new User(
                userRequestDto.userName(),
                userRequestDto.userEmail(),
                userRequestDto.password(),
                userRequestDto.name(),
                userRequestDto.lastName(),
                stringToUserRol(userRequestDto.userRol())
        );
    }

    public UserResponseDto userToResponseDto(User user) {
        if (user == null) return null;
        String role = user.getUserRol() != null ? user.getUserRol().name() : null;
        return new UserResponseDto(user.getUserId(),
                user.getUserEmail(),
                user.getName(),
                user.getLastName(),
                role);
    }

    public static UserReservationDto userToUserReservationDto(User user) {
        if (user == null) return null;
        return new UserReservationDto(
                user.getUserId(),
                user.getUserEmail(),
                user.getName(),
                user.getLastName()
        );
    }

    public static UserReservationDto userResponseDtoToUserReservationDto(UserResponseDto userResponseDto) {
        if (userResponseDto == null) return null;
        return new UserReservationDto(
                userResponseDto.userId(),
                userResponseDto.userEmail(),
                userResponseDto.name(),
                userResponseDto.lastName()
        );
    }

}

