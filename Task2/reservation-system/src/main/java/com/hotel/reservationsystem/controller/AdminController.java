package com.hotel.reservationsystem.controller;

import com.hotel.reservationsystem.enums.ReservationStatus;
import com.hotel.reservationsystem.model.Reservation;
import com.hotel.reservationsystem.model.Room;
import com.hotel.reservationsystem.repository.ReservationRepository;
import com.hotel.reservationsystem.repository.RoomRepository;
import com.hotel.reservationsystem.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private ReservationRepository reservationRepository;
    
    @Autowired
    private ReservationService reservationService;

    // --- Booking Management ---
    @GetMapping("/pending-bookings")
    public ResponseEntity<List<Reservation>> getPendingBookings() {
        return ResponseEntity.ok(reservationRepository.findByStatus(ReservationStatus.PENDING));
    }

    // THIS IS THE MISSING ENDPOINT
    @GetMapping("/bookings/all")
    public ResponseEntity<List<Reservation>> getAllBookings() {
        return ResponseEntity.ok(reservationRepository.findAll());
    }

    @PutMapping("/bookings/{bookingId}/confirm/{roomId}")
    public ResponseEntity<?> confirmBooking(@PathVariable Long bookingId, @PathVariable Long roomId) {
        try {
            Reservation confirmedReservation = reservationService.confirmBooking(bookingId, roomId);
            return ResponseEntity.ok(confirmedReservation);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @PutMapping("/bookings/{bookingId}/reject")
    public ResponseEntity<?> rejectBooking(@PathVariable Long bookingId) {
        try {
            Reservation rejectedReservation = reservationService.rejectBooking(bookingId);
            return ResponseEntity.ok(rejectedReservation);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    // --- Room CRUD Operations ---
    @PostMapping("/rooms")
    public ResponseEntity<Room> addRoom(@RequestBody Room room) {
        if (room.getStatus() == null) {
            room.setStatus(com.hotel.reservationsystem.enums.RoomStatus.AVAILABLE);
        }
        Room savedRoom = roomRepository.save(room);
        return ResponseEntity.ok(savedRoom);
    }

    @GetMapping("/rooms")
    public ResponseEntity<List<Room>> getAllRooms() {
        List<Room> rooms = roomRepository.findAll();
        return ResponseEntity.ok(rooms);
    }

    @PutMapping("/rooms/{id}")
    public ResponseEntity<Room> updateRoom(@PathVariable Long id, @RequestBody Room roomDetails) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found with id: " + id));

        room.setRoomNumber(roomDetails.getRoomNumber());
        room.setCategory(roomDetails.getCategory());
        room.setPricePerNight(roomDetails.getPricePerNight());
        room.setStatus(roomDetails.getStatus());

        Room updatedRoom = roomRepository.save(room);
        return ResponseEntity.ok(updatedRoom);
    }

    @DeleteMapping("/rooms/{id}")
    public ResponseEntity<?> deleteRoom(@PathVariable Long id) {
        roomRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}

