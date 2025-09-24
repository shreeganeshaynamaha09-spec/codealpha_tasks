package com.hotel.reservationsystem.dto;

import com.hotel.reservationsystem.enums.RoomCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor // Lombok annotation to create the constructor
public class AvailabilityResponseDto {

    private RoomCategory category;
    private Long availableCount;

    // The @AllArgsConstructor annotation will automatically generate
    // the constructor that Hibernate needs:
    //
    // public AvailabilityResponseDto(RoomCategory category, Long availableCount) {
    //     this.category = category;
    //     this.availableCount = availableCount;
    // }
}
