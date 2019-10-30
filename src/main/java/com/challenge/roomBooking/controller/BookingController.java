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

import com.challenge.roomBooking.entity.Booking;
import com.challenge.roomBooking.model.BookingDTO;
import com.challenge.roomBooking.service.impl.BookingServiceImpl;
import com.challenge.roomBooking.service.impl.ServicesValidation;

@RestController
@RequestMapping("/api/")
public class BookingController {

	@Autowired
	private BookingServiceImpl service;

	@Autowired
	private ServicesValidation servicesValidation;

	@PostMapping(path = "v1/booking/booking")
	public ResponseEntity<?> booking(@Valid @RequestBody BookingDTO dto) {

		List<String> bookedDays = servicesValidation.setDays(dto);
		String validationMessage = servicesValidation.validations(dto, bookedDays);
		if (!validationMessage.equals("")) {
			return new ResponseEntity<String>(validationMessage, HttpStatus.OK);
		}

		Booking booking = servicesValidation.prepareBooking(dto, bookedDays);

		if (service.book(booking) == null) {
			return new ResponseEntity<Boolean>(false, HttpStatus.OK);
		}
		return new ResponseEntity<String>(
				"Room " + dto.getRoomId() + " reserved from: " + dto.getCheckin() + " to " + dto.getCheckout(),
				HttpStatus.CREATED);
	}

	@GetMapping(path = "v1/booking/bookings")
	public ResponseEntity<?> findAll() {
		List<BookingDTO> booking = service.findAll();
		if (booking == null) {
			return new ResponseEntity<Boolean>(false, HttpStatus.OK);
		}
		return new ResponseEntity<List<BookingDTO>>(booking, HttpStatus.OK);
	}

	@GetMapping(path = "v1/booking/bookingById/{id}")
	public ResponseEntity<?> bookingById(@Valid @PathVariable("id") Long id) {
		BookingDTO booking = service.getBookingDTOById(id);
		if (booking == null) {
			return new ResponseEntity<Boolean>(false, HttpStatus.OK);
		}
		return new ResponseEntity<BookingDTO>(booking, HttpStatus.OK);
	}

	@DeleteMapping(path = "v1/booking/bookings/{id}")
	public ResponseEntity<?> cancel(@Valid @PathVariable("id") Long id) {
		Booking booking = service.getBookingById(id);
		if (booking == null) {
			return new ResponseEntity<String>("Please, check booking number", HttpStatus.OK);
		}
		if (service.cancel(id)) {
			return new ResponseEntity<String>("Booking " + id + " Cancelled", HttpStatus.OK);
		}
		return new ResponseEntity<String>("Please, check booking number", HttpStatus.OK);
	}

	@PutMapping(path = "v1/booking/bookings")
	public ResponseEntity<?> updateBooking(@Valid @RequestBody BookingDTO dto) {
		// pensar nisso com carinho
		return new ResponseEntity<String>("pensar nisso com carinho", HttpStatus.OK);
	}

}
