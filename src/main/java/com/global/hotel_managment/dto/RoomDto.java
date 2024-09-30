package com.global.hotel_managment.dto;

import com.global.hotel_managment.entity.Booking;

import java.math.BigDecimal;
import java.util.List;

public class RoomDto {
    private Long id;
    private Long roomNumber;
    private String roomType;
    private BigDecimal roomPrice;
    private String roomImgUrl;
    private String roomDescription;
    private List<BookingDto> bookings;

}
