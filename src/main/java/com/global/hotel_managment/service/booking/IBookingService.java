package com.global.hotel_managment.service.booking;

import com.global.hotel_managment.dto.BookingDto;
import com.global.hotel_managment.request.AddBookingRequest;

import java.util.List;

public interface IBookingService {

    BookingDto saveBooking( AddBookingRequest bookingRequest);
    List<BookingDto> getBookingsByUserId(Long userId);

    List<BookingDto> getBookingsByRoomId(Long roomId);

    BookingDto findBookingByConfirmationCode(String confirmationCode);

    List<BookingDto> getAllBookings();

    void cancelBooking(Long bookingId);
}
