package com.global.hotel_managment.repository;

import com.global.hotel_managment.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface RoomRepo extends JpaRepository<Room,Long> {
    @Query("SELECT r FROM Room r WHERE r.id NOT IN " +
            "(SELECT b.room.id FROM Booking b WHERE :currentDate BETWEEN b.checkInDate AND b.checkOutDate)")
    List<Room> findAvailableRoomsNow(@Param("currentDate") LocalDate currentDate);



    @Query("SELECT r FROM Room r WHERE r.roomType LIKE %:type% AND r.id NOT IN " +
            "(SELECT b.room.id FROM Booking b WHERE  (b.checkInDate <= :checkOutDate) AND (b.checkOutDate >= :checkInDate))")
    List<Room> findAvailableRoomsByDateAndType(LocalDate checkInDate, LocalDate checkOutDate, String type);

    @Query("SELECT DISTINCT r.roomType FROM Room r")
    List<String> findRoomTypes();


    boolean existsByRoomNumber(Long roomNumber);
}
