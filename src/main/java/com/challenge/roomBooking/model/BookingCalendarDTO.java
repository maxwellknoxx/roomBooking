package com.challenge.roomBooking.model;

import java.time.LocalDate;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class BookingCalendarDTO {
	
	private Long id;
	private Long bookingId;
	List<LocalDate> bookedDays;

}
