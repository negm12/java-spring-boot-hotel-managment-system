package com.global.hotel_managment.request;

import lombok.Data;

@Data
public class UpdateUserRequest {
    private Long id;
    private String firstName;
    private String lastName;
}
