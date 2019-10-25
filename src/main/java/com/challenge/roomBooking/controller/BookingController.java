package com.challenge.roomBooking.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.roomBooking.entity.BookingEntity;
import com.challenge.roomBooking.model.BookingModel;
import com.challenge.roomBooking.service.impl.BookingServiceImpl;
import com.challenge.roomBooking.service.impl.MapValidationErrorService;
import com.challenge.roomBooking.service.impl.ServicesValidation;

@RestController
@RequestMapping("/api/")
public class BookingController {

	@Autowired
	private BookingServiceImpl service;

	@Autowired
	private ServicesValidation servicesValidation;

	@Autowired
	private MapValidationErrorService mapValidationErrorService;

	@PostMapping(path = "v1/booking/booking")
	public ResponseEntity<?> booking(@Valid @RequestBody BookingEntity entity, BindingResult result) {

		ResponseEntity<?> errorMap = mapValidationErrorService.mapValidation(result);
		if (errorMap != null) {
			return errorMap;
		}

		String validationMessage = validations(entity);
		if (!validationMessage.equals("")) {
			return new ResponseEntity<String>(validationMessage, HttpStatus.OK);
		}

		BookingModel booking = service.book(entity);
		if (booking == null) {
			return new ResponseEntity<Boolean>(false, HttpStatus.OK);
		}
		return new ResponseEntity<BookingModel>(booking, HttpStatus.OK);
	}

	@GetMapping(path = "v1/booking/bookings")
	public ResponseEntity<?> findAll() {
		List<BookingModel> booking = service.findAll();
		if (booking == null) {
			return new ResponseEntity<Boolean>(false, HttpStatus.OK);
		}
		return new ResponseEntity<List<BookingModel>>(booking, HttpStatus.OK);
	}

	@DeleteMapping(path = "v1/booking/bookings/{id}")
	public ResponseEntity<?> cancel(@Valid @PathVariable("id") Long id) {
		BookingEntity booking = service.getBookingById(id);
		if (booking == null) {
			return new ResponseEntity<Boolean>(false, HttpStatus.OK);
		}
		if (service.cancel(booking)) {
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		}
		return new ResponseEntity<Boolean>(false, HttpStatus.OK);
	}

	@PutMapping(path = "v1/booking/bookings")
	public ResponseEntity<?> updateBooking(@Valid @RequestBody BookingEntity entity) {
		BookingModel booking = service.updateBooking(entity);
		if (booking == null) {
			return new ResponseEntity<Boolean>(false, HttpStatus.OK);
		}
		return new ResponseEntity<BookingModel>(booking, HttpStatus.OK);
	}

	/**
	 * Applies all validations
	 * 
	 * @param entity
	 * @return
	 */
	public String validations(BookingEntity entity) {
		Long roomId = entity.getRoom().getId();

		if (!isValidRoom(entity)) {
			return "Room " + roomId + " is not valid!";
		}
		
		
		if(!isValidDateFormat(entity)) {
			return "Please, set the date as dd/MM/yyyy";
		}

		if (!isValidPeriod(entity)) {
			return "Check-in cannot be later than check-out";
		}

		String message = isRoomAvailable(entity);
		if (!message.equals("OK")) {
			return message;
		}

		return "";
	}

	/**
	 * Validates whether the room exists
	 * 
	 * @param entity - BookEntity
	 * @return true or false
	 */
	public Boolean isValidRoom(BookingEntity entity) {
		return servicesValidation.isValidRoom(entity.getRoom().getId());
	}

	/**
	 * Validates whether the room is available for that period of time
	 * 
	 * @param entity
	 * @return
	 */
	public String isRoomAvailable(@Valid @RequestBody BookingEntity entity) {
		return servicesValidation.isRoomAvailable(entity.getRoom().getId(), entity.getCheckin());
	}

	/**
	 * Validates whether the time period is valid (check-in date is no higher than
	 * check-out)
	 * 
	 * @param entity
	 * @return
	 */
	public Boolean isValidPeriod(BookingEntity entity) {
		return servicesValidation.isValidPeriod(entity);
	}
	
	/**
	 *  * Validates whether the date is in the format ( dd/MM/yyyy )
	 * @param entity
	 * @return true if both dates are in the format ( dd/MM/yyyy )
	 */
	public Boolean isValidDateFormat(BookingEntity entity) {
		return servicesValidation.isValidDateFormat(entity.getCheckin(), entity.getCheckout());
	}

}
