package com.global.hotel_managment.mapper;

import com.global.hotel_managment.dto.UserDto;
import com.global.hotel_managment.entity.Booking;
import com.global.hotel_managment.entity.User;
import com.global.hotel_managment.response.LoginResponse;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-01T00:17:59+0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 22.0.1 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDto userToUserDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserDto userDto = new UserDto();

        return userDto;
    }

    @Override
    public User userDtoToUser(UserDto userDto) {
        if ( userDto == null ) {
            return null;
        }

        User user = new User();

        return user;
    }

    @Override
    public LoginResponse userToLoginResponse(User user) {
        if ( user == null ) {
            return null;
        }

        LoginResponse loginResponse = new LoginResponse();

        loginResponse.setFirstName( user.getFirstName() );
        loginResponse.setLastName( user.getLastName() );
        loginResponse.setEmail( user.getEmail() );
        loginResponse.setRole( user.getRole() );
        List<Booking> list = user.getBookings();
        if ( list != null ) {
            loginResponse.setBookings( new ArrayList<Booking>( list ) );
        }

        return loginResponse;
    }

    @Override
    public User LoginResponseToUser(LoginResponse loginResponse) {
        if ( loginResponse == null ) {
            return null;
        }

        User user = new User();

        user.setFirstName( loginResponse.getFirstName() );
        user.setLastName( loginResponse.getLastName() );
        user.setEmail( loginResponse.getEmail() );
        user.setRole( loginResponse.getRole() );
        List<Booking> list = loginResponse.getBookings();
        if ( list != null ) {
            user.setBookings( new ArrayList<Booking>( list ) );
        }

        return user;
    }
}
