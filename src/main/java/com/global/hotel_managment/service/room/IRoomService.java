package com.global.hotel_managment.service.room;

import com.global.hotel_managment.dto.BookingDto;
import com.global.hotel_managment.dto.RoomDto;
import com.global.hotel_managment.entity.Room;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface IRoomService {

    List<RoomDto> getAllRooms();

    List<RoomDto> getAvailableRoomsNow();


    List<RoomDto> getAvailableRoomsByDateAndType(LocalDate checkInDate, LocalDate checkOutDate, String type);

    RoomDto getRoomById(Long id);

    Room addRoom(Room room);

    Room updateRoom(Long id,Room room);

    void deleteRoom(Long id);
    List<String> getRoomTypes();

}