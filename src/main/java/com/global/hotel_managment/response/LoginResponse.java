package com.global.hotel_managment.response;

import com.global.hotel_managment.entity.Booking;
import lombok.Data;

import java.util.List;
@Data
public class LoginResponse {
    private String firstName;
    private String lastName;
    private String email;
    private String role;
    private String token;
    List<Booking> bookings;

}
