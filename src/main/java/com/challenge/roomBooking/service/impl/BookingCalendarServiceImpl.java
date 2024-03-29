package com.challenge.roomBooking.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.challenge.roomBooking.entity.BookingCalendar;
import com.challenge.roomBooking.repository.BookingCalendarRepository;
import com.challenge.roomBooking.service.BookingCalendarService;

@Service
@Transactional
public class BookingCalendarServiceImpl implements BookingCalendarService {

	@Autowired
	private BookingCalendarRepository repository;

	@Override
	public List<BookingCalendar> findByBookingId(Long id) {
		return repository.findByBookingId(id);
	}

	@Override
	public Integer deleteByBookingId(Long id) {
		return repository.deleteByBookingId(id);
	}

}
