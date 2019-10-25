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
	 * Validate whether the room exists
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
	 * 
	 * @param id
	 * @return
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
	 * 
	 * @param entity
	 * @return
	 */
	public Boolean isValidPeriod(BookingEntity entity) {
		return DateUtils.isValidDate(entity.getCheckin(), entity.getCheckout());
	}

}
