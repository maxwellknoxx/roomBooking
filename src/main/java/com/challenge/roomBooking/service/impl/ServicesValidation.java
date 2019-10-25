package com.challenge.roomBooking.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.challenge.roomBooking.entity.BookingEntity;
import com.challenge.roomBooking.entity.RoomEntity;
import com.challenge.roomBooking.repository.BookingRepository;
import com.challenge.roomBooking.repository.RoomRepository;
import com.challenge.roomBooking.utils.DateUtils;

@Service
public class ServicesValidation {

	@Autowired
	private RoomRepository roomRepository;

	@Autowired
	private BookingRepository bookingRepository;

	/**
	 * Validates whether the room exists
	 * 
	 * @param id - Room Id
	 * @return true or false
	 */
	public Boolean isValidRoom(Long id) {
		RoomEntity entity = roomRepository.findById(id).orElse(null);
		if (entity == null) {
			return false;
		}
		return true;
	}

	/**
	 * Validates whether the room is available in the desire booked day
	 * 
	 * @param id - Room id
	 * @return 'OK' if the room is available or a message about the next available day
	 */
	public String isRoomAvailable(Long id, String checkin) {
		BookingEntity entity = bookingRepository.findByRoomIdAndCheckin(id, checkin);
		if (entity == null) {
			return "OK";
		}
		return "Room is not available for the date " + entity.getCheckin() + ". It will be available after "
				+ entity.getCheckout();
	}

	/**
	 * Validates whether the date is in the correct format ( dd/MM/yyyy )
	 * 
	 * @param checkin
	 * @param checkout
	 * @return true if it is in the format dd/MM/yyyy
	 */
	public Boolean isValidDateFormat(String checkin, String checkout) {
		return DateUtils.isValidDateFormat(checkin) && DateUtils.isValidDateFormat(checkout);
	}

	/**
	 * Compares whether the check-in and check-out date is valid
	 * 
	 * @param checkin
	 * @param checkout
	 * @return true if the check-in is before the check-out or at the same day
	 */
	public Boolean isValidPeriod(BookingEntity entity) {
		return DateUtils.isValidPeriod(entity.getCheckin(), entity.getCheckout());
	}

}
