package com.challenge.roomBooking.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.challenge.roomBooking.entity.Booking;
import com.challenge.roomBooking.exception.DateException;
import com.challenge.roomBooking.exception.EntityNotFoundException;
import com.challenge.roomBooking.exception.ResourceNotFoundException;
import com.challenge.roomBooking.exception.RoomException;
import com.challenge.roomBooking.model.BookingDTO;
import com.challenge.roomBooking.repository.BookingRepository;
import com.challenge.roomBooking.service.BookingService;
import com.challenge.roomBooking.utils.BookMapper;

@Service
public class BookingServiceImpl implements BookingService {

	@Autowired
	private BookingRepository repository;

	@Autowired
	private ServicesValidation servicesValidation;

	@Override
	public List<BookingDTO> findAll() throws ResourceNotFoundException {
		List<Booking> entities = repository.findAll();
		if (entities.isEmpty()) {
			throw new ResourceNotFoundException(Booking.class, "No bookings found");
		}
		return BookMapper.getListDTO(entities);
	}

	@Override
	public BookingDTO book(BookingDTO dto) throws RoomException, DateException {
		BookingDTO booking = servicesValidation.prepareBooking(dto);

		Booking entity = BookMapper.getEntity(booking);
		return BookMapper.getDTO(repository.save(entity));
	}

	@Override
	public Boolean cancel(Long id) {
		try {
			getBookingById(id);
			repository.deleteById(id);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Booking getBookingById(Long id) {
		return repository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(Booking.class, "id", id.toString()));
	}

	public BookingDTO getBookingDTOById(Long id) {
		return BookMapper.getDTO(repository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(Booking.class, "id", id.toString())));
	}

}
