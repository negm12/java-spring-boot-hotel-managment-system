package com.global.hotel_managment.controller;

import com.global.hotel_managment.dto.UserDto;
import com.global.hotel_managment.error.RecordAlreadyExist;
import com.global.hotel_managment.error.ResourceNotFound;
import com.global.hotel_managment.request.AddUserRequest;
import com.global.hotel_managment.request.LoginRequest;
import com.global.hotel_managment.response.ApiResponse;
import com.global.hotel_managment.response.LoginResponse;
import com.global.hotel_managment.service.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final IUserService userService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody LoginRequest loginRequest){
        try {
            LoginResponse response = userService.login(loginRequest);
            return ResponseEntity.ok(new ApiResponse("login success",response));
        }catch (ResourceNotFound  e){
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(),null));
        }
        catch (RuntimeException e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(),null));
        }
        catch (Exception e){
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse("email or password not correct ",null));
        }

    }

    @PostMapping("/sign-in")
    public ResponseEntity<ApiResponse> signIn(@RequestBody AddUserRequest userRequest){

        try {
            UserDto userDto = userService.signIn(userRequest);
            return ResponseEntity.ok(new ApiResponse("sign in success  ",userDto));
        }catch (RecordAlreadyExist e){
            return ResponseEntity.status(CONFLICT)
                    .body(new ApiResponse(e.getMessage(),null));
        }
        catch (RuntimeException e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(),null));
        }
    }
}
