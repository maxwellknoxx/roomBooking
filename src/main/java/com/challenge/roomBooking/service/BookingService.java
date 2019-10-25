package com.challenge.roomBooking.service;

import java.util.List;

import com.challenge.roomBooking.entity.BookingEntity;
import com.challenge.roomBooking.model.BookingModel;

public interface BookingService {

	List<BookingModel> findAll();
	
	BookingModel book(BookingEntity entity);
	
	Boolean cancel(BookingEntity entity);

}
