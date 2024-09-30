package com.global.hotel_managment.request;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
