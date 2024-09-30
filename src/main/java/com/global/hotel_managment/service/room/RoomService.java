package com.global.hotel_managment.service.room;

import com.global.hotel_managment.dto.RoomDto;
import com.global.hotel_managment.entity.Room;
import com.global.hotel_managment.error.RecordAlreadyExist;
import com.global.hotel_managment.error.ResourceNotFound;
import com.global.hotel_managment.mapper.RoomMapper;
import com.global.hotel_managment.repository.RoomRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService implements  IRoomService{

    private final RoomRepo roomRepo;
    private final RoomMapper roomMapper;

    @Override
    public List<RoomDto> getAllRooms() {
        List<Room> rooms = roomRepo.findAll();
        List<RoomDto> roomDtos = rooms.stream().map(roomMapper::roomToRoomDto).toList();
        return roomDtos;
    }

    @Override
    public List<RoomDto> getAvailableRoomsNow() {
        List<Room> rooms = roomRepo.findAvailableRoomsNow(LocalDate.now());
        List<RoomDto> roomDtos = rooms.stream().map(roomMapper::roomToRoomDto).toList();
        return roomDtos;
    }

    @Override
    public List<RoomDto> getAvailableRoomsByDateAndType
            (LocalDate checkInDate, LocalDate checkOutDate, String type) {

        List<Room> rooms = roomRepo.findAvailableRoomsByDateAndType(checkInDate ,checkOutDate ,type);
        List<RoomDto> roomDtos = rooms.stream().map(roomMapper::roomToRoomDto).toList();
        return roomDtos;
    }

    @Override
    public RoomDto getRoomById(Long id) {
        Room room = roomRepo.findById(id)
                .orElseThrow(()-> new ResourceNotFound("room not found"));

        RoomDto roomDto = roomMapper.roomToRoomDto(room);
        return roomDto;
    }


    @Override
    public Room addRoom(Room room) {
        if(roomRepo.existsByRoomNumber(room.getRoomNumber())){
            throw new RecordAlreadyExist("this room number already used ");
        }
        Room savedRoom = roomRepo.save(room);
        return savedRoom;
    }

    @Override
    public Room updateRoom(Long id , Room roomrequest) {
        Room room = roomRepo.findById(id)
                .orElseThrow(()->new ResourceNotFound("room not found"));

        room.setRoomDescription(roomrequest.getRoomDescription());
        room.setRoomPrice(roomrequest.getRoomPrice());
        room.setRoomNumber(roomrequest.getRoomNumber());
        room.setRoomType(roomrequest.getRoomType());
        room.setRoomImgUrl(roomrequest.getRoomImgUrl());

        roomRepo.save(room);
        return room;
    }

    @Override
    public void deleteRoom(Long id) {
        roomRepo.findById(id).ifPresentOrElse(roomRepo::delete,()->{
            throw new ResourceNotFound("room not found");
        });

    }

    @Override
    public List<String> getRoomTypes() {
        return roomRepo.findRoomTypes();
    }
}
