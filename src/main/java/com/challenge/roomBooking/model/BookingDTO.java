package com.challenge.roomBooking.model;

import java.time.LocalDate;
import java.util.List;

import com.challenge.roomBooking.enums.RoomType;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class BookingDTO {

	private Long id;
	private Long roomId;
	private RoomType roomType;
	private String checkin;
	private String checkout;
	List<LocalDate> booked_days;

}
