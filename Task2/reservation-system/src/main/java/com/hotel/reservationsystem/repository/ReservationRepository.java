package com.hotel.reservationsystem.repository;

import com.hotel.reservationsystem.enums.ReservationStatus;
import com.hotel.reservationsystem.model.Reservation;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    // We can add custom queries here later if needed
	List<Reservation> findByUserId(Long userId);
	List<Reservation> findByStatus(ReservationStatus status);
}