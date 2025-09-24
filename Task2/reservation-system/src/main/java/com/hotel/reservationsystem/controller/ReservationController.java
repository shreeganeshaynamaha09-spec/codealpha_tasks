package com.hotel.reservationsystem.controller;

import com.hotel.reservationsystem.dto.AvailabilityResponseDto;
import com.hotel.reservationsystem.dto.BookingRequestDto;
import com.hotel.reservationsystem.model.Reservation;
import com.hotel.reservationsystem.repository.ReservationRepository;
import com.hotel.reservationsystem.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ReservationRepository reservationRepository;

    /**
     * User-facing endpoint to check the count of available rooms per category for given dates.
     */
    @GetMapping("/search-availability")
    public ResponseEntity<List<AvailabilityResponseDto>> searchRoomAvailability(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkIn,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOut) {

        List<AvailabilityResponseDto> availability = reservationService.findAvailableRoomCounts(checkIn, checkOut);
        return ResponseEntity.ok(availability);
    }

    /**
     * User-facing endpoint to create a new booking request for a specific category.
     * The reservation is created with a PENDING status and no room assigned.
     */
    @PostMapping("/book")
    public ResponseEntity<?> createBooking(@RequestBody BookingRequestDto bookingRequest) {
        try {
            Reservation newReservation = reservationService.createReservation(bookingRequest);
            return ResponseEntity.ok(newReservation);
        } catch (RuntimeException e) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("error", true, "message", e.getMessage()));
        }
    }

    /**
     * User-facing endpoint to retrieve all bookings for a specific user.
     */
    @GetMapping("/my-bookings/{userId}")
    public ResponseEntity<List<Reservation>> getMyBookings(@PathVariable Long userId) {
        List<Reservation> bookings = reservationRepository.findByUserId(userId);
        return ResponseEntity.ok(bookings);
    }
}
