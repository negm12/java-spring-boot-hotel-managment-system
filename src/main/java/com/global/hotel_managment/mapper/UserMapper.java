package com.global.hotel_managment.mapper;

import com.global.hotel_managment.dto.UserDto;
import com.global.hotel_managment.entity.User;
import com.global.hotel_managment.response.LoginResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto userToUserDto(User user);

    User userDtoToUser(UserDto userDto);

    LoginResponse userToLoginResponse(User user);

    User LoginResponseToUser(LoginResponse loginResponse);
}
