package com.example.hotel_reservation.mapper.user;

import com.example.hotel_reservation.dto.user.UserRequestDto;
import com.example.hotel_reservation.dto.user.UserResponseDto;
import com.example.hotel_reservation.entity.User;
import com.example.hotel_reservation.entity.UserRol;
import org.springframework.stereotype.Component;

@Component
public class UserMappers {

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

}
