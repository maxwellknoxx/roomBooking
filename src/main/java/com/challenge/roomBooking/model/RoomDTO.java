package com.challenge.roomBooking.model;

import java.util.List;

import com.challenge.roomBooking.enums.RoomType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RoomDTO {
	
	private Long id;
	private RoomType type;
	private List<BookingDTO> bookings;

}
