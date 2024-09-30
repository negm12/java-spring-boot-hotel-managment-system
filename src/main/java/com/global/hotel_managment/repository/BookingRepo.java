package com.global.hotel_managment.repository;

import com.global.hotel_managment.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepo extends JpaRepository<Booking,Long> {
    List<Booking> findByUserId(Long userId);

    List<Booking> findByRoomId(Long roomId);

    Booking findByBookingConfirmationCode(String confirmationCode);
}
