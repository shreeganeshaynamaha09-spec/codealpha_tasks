package com.hotel.reservationsystem.repository;

import com.hotel.reservationsystem.dto.AvailabilityResponseDto;
import com.hotel.reservationsystem.enums.RoomCategory;
import com.hotel.reservationsystem.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    // ADD A SPACE right before "(SELECT..."
    @Query("SELECT r FROM Room r WHERE r.category = :category AND r.id NOT IN (" +
           "SELECT res.room.id FROM Reservation res WHERE " +
           "res.status IN ('CONFIRMED', 'PENDING') AND " +
           "((res.checkInDate < :endDate) AND (res.checkOutDate > :startDate))" +
           ")")
    List<Room> findAvailableRooms(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("category") RoomCategory category
    );
    
    @Query("SELECT new com.hotel.reservationsystem.dto.AvailabilityResponseDto(r.category, COUNT(r)) " +
    	       "FROM Room r WHERE r.status = 'AVAILABLE' AND r.id NOT IN (" +
    	       "SELECT res.room.id FROM Reservation res WHERE res.room IS NOT NULL AND " +
    	       "res.status IN ('CONFIRMED', 'PENDING') AND " +
    	       "((res.checkInDate < :endDate) AND (res.checkOutDate > :startDate))" +
    	       ") GROUP BY r.category")
    	List<AvailabilityResponseDto> findAvailableRoomCounts(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
 // Add this new method to find a representative room for a category
    Optional<Room> findFirstByCategory(RoomCategory category);

}