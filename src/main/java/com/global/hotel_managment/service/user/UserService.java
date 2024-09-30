package com.global.hotel_managment.service.user;

import com.global.hotel_managment.dto.UserDto;
import com.global.hotel_managment.entity.User;
import com.global.hotel_managment.error.RecordAlreadyExist;
import com.global.hotel_managment.error.ResourceNotFound;
import com.global.hotel_managment.mapper.UserMapper;
import com.global.hotel_managment.repository.UserRepo;
import com.global.hotel_managment.request.AddUserRequest;
import com.global.hotel_managment.request.LoginRequest;
import com.global.hotel_managment.request.UpdateUserRequest;
import com.global.hotel_managment.response.LoginResponse;
import com.global.hotel_managment.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{

    private final UserRepo userRepo;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepo.findAll();
       List<UserDto> userDtos = users.stream().map(userMapper::userToUserDto).toList();
        return userDtos;
    }

    @Override
    public UserDto getUserById(Long id) {
        User user = userRepo.findById(id).orElseThrow(()->new ResourceNotFound("user not found"));
        UserDto userDto = userMapper.userToUserDto(user);
        return userDto;
    }

    @Override
    public UserDto getUserByEmail(String email) {
        User user = userRepo.findByEmail(email);
        UserDto userDto = userMapper.userToUserDto(user);
        return userDto;
    }

    @Override
    public UserDto addAdmin(AddUserRequest userRequest) {
        if(userRepo.existsByEmail(userRequest.getEmail())){
            throw new RecordAlreadyExist(userRequest.getEmail()+"already exist");
        }
        User user = new User();
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setRole("ADMIN");
        userRepo.save(user);
        UserDto userDto = userMapper.userToUserDto(user);
        return userDto;
    }

    @Override
    public void deleteUser(Long id) {
        userRepo.findById(id).ifPresentOrElse(userRepo::delete, ()->{
            throw new ResourceNotFound("user not found");
        });
    }

    @Override
    public UserDto updateUser(UpdateUserRequest userRequest) {
        User user = userRepo.findById(userRequest.getId()).orElseThrow(()->new ResourceNotFound("user not found"));
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        userRepo.save(user);
        UserDto userDto = userMapper.userToUserDto(user);
        return userDto;
    }

    @Override
    public UserDto signIn(AddUserRequest userRequest) {
        if(userRepo.existsByEmail(userRequest.getEmail())){
            throw new RecordAlreadyExist(userRequest.getEmail()+"already exist");
        }
        User user = new User();
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setRole("USER");
        userRepo.save(user);
        UserDto userDto = userMapper.userToUserDto(user);
        return userDto;
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(), loginRequest.getPassword()));

            var user = userRepo.findByEmail(loginRequest.getEmail());
            if(user == null){
                throw new ResourceNotFound("user not found ");
            }
            var token = jwtUtil.generateToken(user.getUsername());
            LoginResponse loginResponse = userMapper.userToLoginResponse(user);
            loginResponse.setToken(token);

        return loginResponse;
    }
}
