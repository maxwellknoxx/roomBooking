package com.challenge.roomBooking.service;

import java.util.List;

import com.challenge.roomBooking.entity.BookingCalendar;

public interface BookingCalendarService {
	
	List<BookingCalendar> findByBookingId(Long id);

}
