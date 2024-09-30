package com.global.hotel_managment.controller;

import com.global.hotel_managment.dto.RoomDto;
import com.global.hotel_managment.entity.Room;
import com.global.hotel_managment.error.RecordAlreadyExist;
import com.global.hotel_managment.error.ResourceNotFound;
import com.global.hotel_managment.response.ApiResponse;
import com.global.hotel_managment.service.room.IRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/rooms")
@RequiredArgsConstructor
public class RoomController {
    private final IRoomService roomService;

    @GetMapping
    public ResponseEntity<ApiResponse> getAllRooms(){
      try {
          List<RoomDto> roomDtos = roomService.getAllRooms();
          return ResponseEntity.ok(new ApiResponse("get all rooms success ",roomDtos));
      }catch (RuntimeException e){
          return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                  .body(new ApiResponse(e.getMessage(),null));
      }
    }

    @GetMapping("/get-available-rooms-now")
    public ResponseEntity<ApiResponse> getAvailableRoomsNow(){
        try {
            List<RoomDto> roomDtos = roomService.getAvailableRoomsNow();
            return ResponseEntity.ok(new ApiResponse("get available rooms now success ",roomDtos));
        }catch (RuntimeException e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/get-available-rooms-by-date-and-type")
    public ResponseEntity<ApiResponse> getAvailableRoomsByDateAndType
            (@RequestParam LocalDate checkInDate ,
             @RequestParam  LocalDate checkOutDate ,
             @RequestParam  String type) {

        try {
            List<RoomDto> roomDtos = roomService.getAvailableRoomsByDateAndType
                    (checkInDate , checkOutDate , type);

            return ResponseEntity.ok(new ApiResponse("get available rooms by date and type success ",
                    roomDtos));
        }catch (RuntimeException e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getRoomById(@PathVariable Long id) {

        try {
            RoomDto roomDto = roomService.getRoomById(id);

            return ResponseEntity.ok(new ApiResponse("get available rooms by id success ", roomDto));

        }catch (ResourceNotFound e){
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(),null));
        } catch (RuntimeException e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse> addRoom(@RequestBody Room room) {

        try {


            return ResponseEntity.ok(new ApiResponse("room saved success ",
                    roomService.addRoom(room)));

        }catch (RecordAlreadyExist e){
            return ResponseEntity.status(CONFLICT)
                    .body(new ApiResponse(e.getMessage(),null));
        } catch (RuntimeException e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateRoom(@PathVariable Long id,@RequestBody Room room) {

        try {
            return ResponseEntity.ok(new ApiResponse("room updated success ",
                    roomService.updateRoom(id,room)));

        }catch (ResourceNotFound e){
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(),null));
        } catch (RuntimeException e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(),null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteRoom(@PathVariable Long id) {

        roomService.deleteRoom(id);
        try {
            return ResponseEntity.ok(new ApiResponse("room updated success ",null));

        }catch (ResourceNotFound e){
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(),null));
        } catch (RuntimeException e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/get-room-types")
    public ResponseEntity<ApiResponse> getRoomTypes() {
        try {
            return ResponseEntity.ok(new ApiResponse("room updated success ",
                    roomService.getRoomTypes()));

        } catch (RuntimeException e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(),null));
        }
    }


}
