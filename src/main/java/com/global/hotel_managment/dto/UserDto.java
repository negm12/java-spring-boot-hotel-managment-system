package com.global.hotel_managment.dto;

import com.global.hotel_managment.entity.Booking;

import java.util.List;

public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String role;
    List<BookingDto> bookings;

}
