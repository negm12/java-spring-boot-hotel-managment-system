package com.global.hotel_managment.mapper;

import com.global.hotel_managment.dto.RoomDto;
import com.global.hotel_managment.entity.Room;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RoomMapper {
    RoomMapper INSTANCE = Mappers.getMapper(RoomMapper.class);

    RoomDto roomToRoomDto(Room room);

    Room roomDtoToRoom(RoomDto roomDto);
}
