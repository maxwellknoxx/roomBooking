package com.challenge.roomBooking.service;

import java.util.List;

import com.challenge.roomBooking.exception.DateException;
import com.challenge.roomBooking.exception.RoomException;
import com.challenge.roomBooking.model.BookingDTO;

public interface BookingService {

	List<BookingDTO> findAll();
	
	BookingDTO book(BookingDTO dto) throws RoomException, DateException;
	
	Boolean cancel(Long id);

}
