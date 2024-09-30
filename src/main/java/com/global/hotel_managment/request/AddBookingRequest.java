package com.global.hotel_managment.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AddBookingRequest {
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private int numOfPersons;
    private int numOfChildren;
    private Long userID;
    private Long roomID;
}
