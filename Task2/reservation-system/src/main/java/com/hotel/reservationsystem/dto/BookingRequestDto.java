package com.hotel.reservationsystem.dto;

import com.hotel.reservationsystem.enums.RoomCategory;
import lombok.Data;
import java.time.LocalDate;

@Data
public class BookingRequestDto {

    private Long userId;
    private RoomCategory category;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public RoomCategory getCategory() {
		return category;
	}
	public void setCategory(RoomCategory category) {
		this.category = category;
	}
	public LocalDate getCheckInDate() {
		return checkInDate;
	}
	public void setCheckInDate(LocalDate checkInDate) {
		this.checkInDate = checkInDate;
	}
	public LocalDate getCheckOutDate() {
		return checkOutDate;
	}
	public void setCheckOutDate(LocalDate checkOutDate) {
		this.checkOutDate = checkOutDate;
	}
    
}

