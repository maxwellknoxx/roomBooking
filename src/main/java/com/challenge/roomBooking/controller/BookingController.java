package com.challenge.roomBooking.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.roomBooking.exception.DateException;
import com.challenge.roomBooking.exception.ResourceNotFoundException;
import com.challenge.roomBooking.exception.RoomException;
import com.challenge.roomBooking.model.BookingDTO;
import com.challenge.roomBooking.service.impl.BookingCalendarServiceImpl;
import com.challenge.roomBooking.service.impl.BookingServiceImpl;

@RestController
@RequestMapping("/api/")
public class BookingController {

	@Autowired
	private BookingServiceImpl service;

	@Autowired
	private BookingCalendarServiceImpl bookingCalendarService;

	@PostMapping(path = "v1/booking/booking")
	public ResponseEntity<?> booking(@Valid @RequestBody BookingDTO dto) throws DateException, RoomException {
		BookingDTO booked = service.book(dto);
		if (booked == null) {
			return new ResponseEntity<Boolean>(false, HttpStatus.OK);
		}

		return new ResponseEntity<String>(roomBookedMessage(booked.getId(), booked.getRoomId(),
				booked.getCheckin(), booked.getCheckout()), HttpStatus.CREATED);
	}

	@PutMapping(path = "v1/booking/booking")
	public ResponseEntity<?> updateBooking(@Valid @RequestBody BookingDTO dto) {
		bookingCalendarService.deleteByBookingId(dto.getId());
		service.cancel(dto.getId());

		BookingDTO booked = service.book(dto);
		if (booked == null) {
			return new ResponseEntity<Boolean>(false, HttpStatus.OK);
		}

		return new ResponseEntity<String>(roomBookedUpdatedMessage(booked.getId(), booked.getRoomId(),
				booked.getCheckin(), booked.getCheckout()), HttpStatus.OK);
	}

	@GetMapping(path = "v1/booking/bookings")
	public ResponseEntity<?> findAll() throws ResourceNotFoundException {
		List<BookingDTO> booking = service.findAll();

		return new ResponseEntity<List<BookingDTO>>(booking, HttpStatus.OK);
	}

	@GetMapping(path = "v1/booking/booking/{id}")
	public ResponseEntity<?> bookingById(@Valid @PathVariable("id") Long id) {
		BookingDTO booking = service.getBookingDTOById(id);

		return new ResponseEntity<BookingDTO>(booking, HttpStatus.OK);
	}

	@DeleteMapping(path = "v1/booking/booking/{id}")
	public ResponseEntity<?> cancel(@Valid @PathVariable("id") Long id) {
		service.cancel(id);
		return new ResponseEntity<String>(bookCancelledMessage(id), HttpStatus.OK);
	}
	
	private String roomBookedMessage(Long bookingId, Long roomId, String checkin, String checkout) {
		return "Booking number: " + bookingId + " \nRoom: " + roomId + " \nReserve from: " + checkin + " to "
				+ checkout;
	}

	private String roomBookedUpdatedMessage(Long bookingId, Long roomId, String checkin, String checkout) {
		return "Booking number updated: " + bookingId + " \nRoom: " + roomId + " \nReserve from: " + checkin + " to "
				+ checkout;
	}

	private String bookCancelledMessage(Long id) {
		return "Booking " + id + " cancelled";
	}

}
