package com.global.hotel_managment.mapper;

import com.global.hotel_managment.dto.BookingDto;
import com.global.hotel_managment.entity.Booking;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-01T00:17:59+0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 22.0.1 (Oracle Corporation)"
)
@Component
public class BookingMapperImpl implements BookingMapper {

    @Override
    public BookingDto bookingToBookingDto(Booking booking) {
        if ( booking == null ) {
            return null;
        }

        BookingDto bookingDto = new BookingDto();

        return bookingDto;
    }

    @Override
    public Booking bookingDtoToBooking(BookingDto bookingDto) {
        if ( bookingDto == null ) {
            return null;
        }

        Booking booking = new Booking();

        return booking;
    }
}
