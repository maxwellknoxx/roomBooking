package com.challenge.roomBooking.model;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingCalendarDTO {
	
	private Long id;
	private Long bookingId;
	private String checkin;
	private String checkout;
	private String day;
	List<LocalDate> bookedDays;

}
