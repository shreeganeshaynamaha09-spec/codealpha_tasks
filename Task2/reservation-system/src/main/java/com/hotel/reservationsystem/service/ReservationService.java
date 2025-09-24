package com.hotel.reservationsystem.service;

import com.hotel.reservationsystem.dto.AvailabilityResponseDto;
import com.hotel.reservationsystem.dto.BookingRequestDto;
import com.hotel.reservationsystem.enums.ReservationStatus;
import com.hotel.reservationsystem.enums.RoomCategory;
import com.hotel.reservationsystem.enums.RoomStatus;
import com.hotel.reservationsystem.model.Reservation;
import com.hotel.reservationsystem.model.Room;
import com.hotel.reservationsystem.model.User;
import com.hotel.reservationsystem.repository.ReservationRepository;
import com.hotel.reservationsystem.repository.RoomRepository;
import com.hotel.reservationsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional; // Add this import

@Service
public class ReservationService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    public List<Room> findAvailableRooms(LocalDate checkIn, LocalDate checkOut, RoomCategory category) {
        return roomRepository.findAvailableRooms(checkIn, checkOut, category);
    }

    public List<AvailabilityResponseDto> findAvailableRoomCounts(LocalDate checkIn, LocalDate checkOut) {
        return roomRepository.findAvailableRoomCounts(checkIn, checkOut);
    }

    // THIS IS THE CORRECTED METHOD
    public Reservation createReservation(BookingRequestDto bookingRequest) {
        // 1. Find the user
        User user = userRepository.findById(bookingRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + bookingRequest.getUserId()));

        // 2. Calculate a preliminary total cost based on the category's price
        Room sampleRoom = roomRepository.findFirstByCategory(bookingRequest.getCategory())
                .orElseThrow(() -> new RuntimeException("No rooms available for category: " + bookingRequest.getCategory()));
        
        long numberOfNights = ChronoUnit.DAYS.between(bookingRequest.getCheckInDate(), bookingRequest.getCheckOutDate());
        double totalCost = sampleRoom.getPricePerNight() * numberOfNights;
     

        // 3. Create and populate the reservation object
        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setCheckInDate(bookingRequest.getCheckInDate());
        reservation.setCheckOutDate(bookingRequest.getCheckOutDate());
        reservation.setTotalCost(totalCost);
        reservation.setStatus(ReservationStatus.PENDING);
        
        // ** THE CRITICAL FIX IS HERE **
        // Set the requested category from the DTO onto the reservation entity
        reservation.setRequestedCategory(bookingRequest.getCategory());
        
        // The room is null because an admin has not assigned one yet
        reservation.setRoom(null);

        // 4. Save the reservation to the database
        return reservationRepository.save(reservation);
    }
    public Reservation confirmBooking(Long bookingId, Long roomId) {
        // Find the pending reservation
        Reservation reservation = reservationRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Reservation not found with ID: " + bookingId));

        // Find the selected room
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found with ID: " + roomId));

        // Check if the room is available
        if (room.getStatus() != RoomStatus.AVAILABLE) {
            throw new RuntimeException("Room " + room.getRoomNumber() + " is not available for assignment.");
        }

        // Update reservation
        reservation.setRoom(room);
        reservation.setStatus(ReservationStatus.CONFIRMED);

        // Update room status
        room.setStatus(RoomStatus.OCCUPIED);
        roomRepository.save(room);

        return reservationRepository.save(reservation);
    }

    public Reservation rejectBooking(Long bookingId) {
        Reservation reservation = reservationRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Reservation not found with ID: " + bookingId));
        
        reservation.setStatus(ReservationStatus.CANCELLED);
        return reservationRepository.save(reservation);
    }
}
