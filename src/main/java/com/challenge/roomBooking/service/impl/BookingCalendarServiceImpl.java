package com.challenge.roomBooking.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.challenge.roomBooking.entity.BookingCalendar;
import com.challenge.roomBooking.repository.BookingCalendarRepository;
import com.challenge.roomBooking.service.BookingCalendarService;

@Service
public class BookingCalendarServiceImpl implements BookingCalendarService {

	@Autowired
	private BookingCalendarRepository repository;

	@Override
	public List<BookingCalendar> findByBookingId(Long id) {
		return repository.findByBookingId(id);
	}

}
