package com.challenge.roomBooking.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.challenge.roomBooking.entity.Booking;
import com.challenge.roomBooking.model.BookingDTO;
import com.challenge.roomBooking.repository.BookingRepository;
import com.challenge.roomBooking.service.BookingService;
import com.challenge.roomBooking.utils.BookMapper;

@Service
public class BookingServiceImpl implements BookingService {

	@Autowired
	private BookingRepository repository;

	@Override
	public List<BookingDTO> findAll() {
		List<Booking> entities = repository.findAll();
		if (entities.isEmpty()) {
			return null;
		}
		return BookMapper.getListModel(entities);
	}

	@Override
	public BookingDTO book(Booking entity) {
		Booking bookingEntity = repository.save(entity);
		if (bookingEntity == null) {
			return null;
		}
		return BookMapper.getModel(bookingEntity);
	}

	@Override
	public Boolean cancel(Booking entity) {
		try {
			repository.delete(entity);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public Booking getBookingById(Long id) {
		return repository.findById(id).orElse(null);
	}
	
	public BookingDTO getBookingDTOById(Long id) {
		Booking bookingEntity = repository.findById(id).orElse(null);
		if (bookingEntity == null) {
			return null;
		}
		return BookMapper.getModel(bookingEntity);
	}
	
	public BookingDTO updateBooking(Booking entity) {
		Booking bookingEntity = repository.save(entity);
		if (bookingEntity == null) {
			return null;
		}
		return BookMapper.getModel(bookingEntity);
	}

}
