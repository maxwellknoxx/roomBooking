package com.challenge.roomBooking.service;

import java.util.List;

import com.challenge.roomBooking.entity.Booking;
import com.challenge.roomBooking.model.BookingDTO;

public interface BookingService {

	List<BookingDTO> findAll();
	
	BookingDTO book(Booking entity);
	
	Boolean cancel(Booking entity);

}
