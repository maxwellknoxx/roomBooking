package com.challenge.roomBooking.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.challenge.roomBooking.entity.BookingEntity;
import com.challenge.roomBooking.model.BookingModel;
import com.challenge.roomBooking.repository.BookingRepository;
import com.challenge.roomBooking.service.BookingService;
import com.challenge.roomBooking.utils.BookMapper;

@Service
public class BookingServiceImpl implements BookingService {

	@Autowired
	private BookingRepository repository;

	@Override
	public List<BookingModel> findAll() {
		List<BookingEntity> entities = repository.findAll();
		if (entities.isEmpty()) {
			return null;
		}
		return BookMapper.getListModel(entities);
	}

	@Override
	public BookingModel book(BookingEntity entity) {
		BookingEntity bookingEntity = repository.save(entity);
		if (bookingEntity == null) {
			return null;
		}
		return BookMapper.getModel(bookingEntity);
	}

	@Override
	public Boolean cancel(BookingEntity entity) {
		try {
			repository.delete(entity);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public BookingEntity getBookingById(Long id) {
		BookingEntity entity = repository.findById(id).orElse(null);
		if(entity == null) {
			return null;
		}
		return entity;
	}
	
	public BookingModel updateBooking(BookingEntity entity) {
		BookingEntity bookingEntity = repository.save(entity);
		if (bookingEntity == null) {
			return null;
		}
		return BookMapper.getModel(bookingEntity);
	}

}
