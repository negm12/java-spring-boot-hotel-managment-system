package com.global.hotel_managment.controller;

import com.global.hotel_managment.dto.UserDto;
import com.global.hotel_managment.error.RecordAlreadyExist;
import com.global.hotel_managment.error.ResourceNotFound;
import com.global.hotel_managment.request.AddUserRequest;
import com.global.hotel_managment.request.UpdateUserRequest;
import com.global.hotel_managment.response.ApiResponse;
import com.global.hotel_managment.service.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;

    @GetMapping
    public ResponseEntity<ApiResponse> getAllUsers(){
        try {
            List<UserDto> userDtos = userService.getAllUsers();
            return ResponseEntity.ok(new ApiResponse("get all user success",userDtos));

        }catch (RuntimeException e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable Long id){
        try {
            UserDto userDto = userService.getUserById(id);
            return ResponseEntity.ok(new ApiResponse("get user success",userDto));

        }catch (ResourceNotFound  e){
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(),null));
        }
        catch (RuntimeException e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/by-email")
    public ResponseEntity<ApiResponse> getUserByEmail(@RequestParam String email){
        try {
            UserDto userDto = userService.getUserByEmail(email);
            return ResponseEntity.ok(new ApiResponse("get user success",userDto));

        }catch (ResourceNotFound  e){
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(),null));
        }
        catch (RuntimeException e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateUser(@RequestBody UpdateUserRequest userRequest){

        try {
            UserDto userDto = userService.updateUser(userRequest);
            return ResponseEntity.ok(new ApiResponse("get user success",userDto));

        }catch (ResourceNotFound  e){
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(),null));
        }
        catch (RuntimeException e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(),null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> DeleteUser(@PathVariable Long id){
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok(new ApiResponse("user deleted success",null));

        }catch (ResourceNotFound  e){
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(),null));
        }
        catch (RuntimeException e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PostMapping("/add-admin")
    public ResponseEntity<ApiResponse> addAdmin(@RequestBody AddUserRequest userRequest){

        try {
            UserDto userDto = userService.addAdmin(userRequest);
            return ResponseEntity.ok(new ApiResponse("Admin Added success",userDto));
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
