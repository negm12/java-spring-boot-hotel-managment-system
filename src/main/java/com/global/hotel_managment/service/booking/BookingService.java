package com.global.hotel_managment.service.booking;

import com.global.hotel_managment.dto.BookingDto;
import com.global.hotel_managment.entity.Booking;
import com.global.hotel_managment.entity.Room;
import com.global.hotel_managment.entity.User;
import com.global.hotel_managment.error.RecordAlreadyExist;
import com.global.hotel_managment.error.ResourceNotFound;
import com.global.hotel_managment.mapper.BookingMapper;
import com.global.hotel_managment.repository.BookingRepo;
import com.global.hotel_managment.repository.RoomRepo;
import com.global.hotel_managment.repository.UserRepo;
import com.global.hotel_managment.request.AddBookingRequest;
import com.global.hotel_managment.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService implements IBookingService{

    private final BookingRepo bookingRepo;
    private final UserRepo userRepo;
    private final RoomRepo roomRepo;
    private final BookingMapper bookingMapper;


    @Override
    public BookingDto saveBooking(AddBookingRequest bookingRequest) {
        Booking booking = new Booking();

        User user =  userRepo.findById(bookingRequest.getUserID())
                .orElseThrow(()->new ResourceNotFound("user not found"));
        Room room = roomRepo.findById(bookingRequest.getRoomID())
                .orElseThrow(()->new ResourceNotFound("room not found"));

        List<Booking> existengBooking = room.getBookings();
        if (!roomIsAvailable(bookingRequest,existengBooking)){
            throw new RecordAlreadyExist("room not available for this range of time");
        }

//        check if this room not available between range of date


        String confermationCode = Utils.generateRandomText(10);

        booking.setRoom(room);
        booking.setUser(user);
        booking.setCheckInDate(bookingRequest.getCheckInDate());
        booking.setCheckOutDate(bookingRequest.getCheckOutDate());
        booking.setNumOfPersons(bookingRequest.getNumOfPersons());
        booking.setNumOfChildren(bookingRequest.getNumOfChildren());
        booking.setBookingConfirmationCode(confermationCode);

        bookingRepo.save(booking);
        BookingDto bookingDto = bookingMapper.bookingToBookingDto(booking);

        return bookingDto;
    }

    @Override
    public List<BookingDto> getBookingsByUserId(Long userId) {
        List<Booking> userBookings = bookingRepo.findByUserId(userId);
        List<BookingDto> userBookingDtos = userBookings.stream().map(bookingMapper::bookingToBookingDto).toList();

        return userBookingDtos;
    }

    @Override
    public List<BookingDto> getBookingsByRoomId(Long roomId) {
        List<Booking> roomBookings = bookingRepo.findByRoomId(roomId);
        List<BookingDto> roomBookingDtos = roomBookings.stream().map(bookingMapper::bookingToBookingDto).toList();

        return roomBookingDtos;
    }

    @Override
    public BookingDto findBookingByConfirmationCode(String confirmationCode) {
        Booking booking = bookingRepo.findByBookingConfirmationCode(confirmationCode);
        if(booking == null){
            throw new ResourceNotFound("no booking exist for this confirmation code");
        }
        BookingDto bookingDto = bookingMapper.bookingToBookingDto(booking);
        return bookingDto;
    }

    @Override
    public List<BookingDto> getAllBookings() {
        List<Booking> bookings = bookingRepo.findAll();
        List<BookingDto> bookingDtos = bookings.stream().map(bookingMapper::bookingToBookingDto).toList();

        return bookingDtos;
    }

    @Override
    public void cancelBooking(Long bookingId) {
        bookingRepo.findById(bookingId).ifPresentOrElse(bookingRepo::delete, ()->{
            throw new ResourceNotFound("no booking found");
        });

    }




    private boolean roomIsAvailable(AddBookingRequest bookingRequest, List<Booking> existingBookings) {

        return existingBookings.stream()
            .noneMatch(existingBooking ->
                bookingRequest.getCheckInDate().equals(existingBooking.getCheckInDate())
                    || bookingRequest.getCheckOutDate().isBefore(existingBooking.getCheckOutDate())
                    || (bookingRequest.getCheckInDate().isAfter(existingBooking.getCheckInDate())
                    && bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckOutDate()))
                    || (bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckInDate())

                    && bookingRequest.getCheckOutDate().equals(existingBooking.getCheckOutDate()))
                    || (bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckInDate())

                    && bookingRequest.getCheckOutDate().isAfter(existingBooking.getCheckOutDate()))

                    || (bookingRequest.getCheckInDate().equals(existingBooking.getCheckOutDate())
                    && bookingRequest.getCheckOutDate().equals(existingBooking.getCheckInDate()))

                    || (bookingRequest.getCheckInDate().equals(existingBooking.getCheckOutDate())
                    && bookingRequest.getCheckOutDate().equals(bookingRequest.getCheckInDate()))
            );
    }
}
