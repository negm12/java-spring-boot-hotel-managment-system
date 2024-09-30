package com.global.hotel_managment.service.user;

import com.global.hotel_managment.dto.UserDto;
import com.global.hotel_managment.entity.User;
import com.global.hotel_managment.request.AddUserRequest;
import com.global.hotel_managment.request.LoginRequest;
import com.global.hotel_managment.request.UpdateUserRequest;
import com.global.hotel_managment.response.LoginResponse;

import java.util.List;

public interface IUserService {

    public List<UserDto> getAllUsers();
    public UserDto getUserById(Long id);
    public UserDto getUserByEmail(String email);

    public UserDto addAdmin(AddUserRequest userRequest);

    public void deleteUser(Long id);
    public UserDto updateUser(UpdateUserRequest userRequest);


    public UserDto signIn(AddUserRequest userRequest);
    public LoginResponse login(LoginRequest loginRequest);

}
