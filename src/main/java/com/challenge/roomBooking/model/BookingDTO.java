package com.challenge.roomBooking.model;

import java.time.LocalDate;
import java.util.List;

import com.challenge.roomBooking.enums.RoomType;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingDTO {

	private Long id;
	private Long roomId;
	private RoomType roomType;
	private String checkin;
	private String checkout;
	List<LocalDate> bookedDays;

	@JsonIgnore
	private String roomMessage;

	@JsonIgnore
	private String dateMessage;

	@JsonIgnore
	private List<BookingCalendarDTO> bookingsCalendar;

}
