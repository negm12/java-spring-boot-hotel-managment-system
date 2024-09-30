package com.global.hotel_managment.controller;

import com.global.hotel_managment.error.RecordAlreadyExist;
import com.global.hotel_managment.error.ResourceNotFound;
import com.global.hotel_managment.request.AddBookingRequest;
import com.global.hotel_managment.response.ApiResponse;
import com.global.hotel_managment.service.booking.IBookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/booking")
@RequiredArgsConstructor
public class BookingController {
    private final IBookingService bookingService;

    @PostMapping
    public ResponseEntity<ApiResponse> saveBooking(@RequestBody AddBookingRequest bookingRequest){
        try {
            return ResponseEntity.ok(new ApiResponse("get all bookings",
                    bookingService.saveBooking(bookingRequest)));

        }catch (RecordAlreadyExist e){
            return ResponseEntity.status(CONFLICT)
                    .body(new ApiResponse(e.getMessage(),null));
        }
        catch (ResourceNotFound e){
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(),null));
        }
        catch (RuntimeException e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllBookings(){
        try {
            return ResponseEntity.ok(new ApiResponse("get all bookings",
                    bookingService.getAllBookings()));
        }catch (RuntimeException e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse> getBookingByUserId(@PathVariable Long userId){
        try {
            return ResponseEntity.ok(new ApiResponse("get user bookings ",
                    bookingService.getBookingsByUserId(userId)));
        }catch (RuntimeException e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/room/{roomId}")
    public ResponseEntity<ApiResponse> getBookingByRoomId(@PathVariable Long roomId){
        try {
            return ResponseEntity.ok(new ApiResponse("get room bookings ",
                    bookingService.getBookingsByRoomId(roomId)));
        }catch (RuntimeException e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/{confirmationCode}")
    public ResponseEntity<ApiResponse> getBookingByRoomId(@PathVariable String confirmationCode){
        try {
            return ResponseEntity.ok(new ApiResponse("get booking by confirmation code",
                    bookingService.findBookingByConfirmationCode(confirmationCode)));
        }catch (ResourceNotFound e){
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(),null));
        }
        catch (RuntimeException e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(),null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> cancleBooking(@PathVariable Long id){
        try {
            bookingService.cancelBooking(id);
            return ResponseEntity.ok(new ApiResponse("booking canceled success", null));
        }catch (ResourceNotFound e){
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(),null));
        }
        catch (RuntimeException e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(),null));
        }
    }


}
