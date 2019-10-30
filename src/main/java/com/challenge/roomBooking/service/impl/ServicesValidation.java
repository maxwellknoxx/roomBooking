package com.challenge.roomBooking.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.challenge.roomBooking.entity.Booking;
import com.challenge.roomBooking.entity.BookingCalendar;
import com.challenge.roomBooking.entity.Room;
import com.challenge.roomBooking.model.BookingDTO;
import com.challenge.roomBooking.repository.BookingCalendarRepository;
import com.challenge.roomBooking.repository.RoomRepository;
import com.challenge.roomBooking.utils.BookMapper;
import com.challenge.roomBooking.utils.DateUtils;

@Service
public class ServicesValidation {

	@Autowired
	private RoomRepository roomRepository;

	@Autowired
	private BookingCalendarRepository bookingCalendarRepository;

	public List<String> setDays(BookingDTO dto) {
		return DateUtils.setDays(dto);
	}

	/**
	 * Validates whether the room exists
	 * 
	 * @param id - Room Id
	 * @return true or false
	 */
	public Boolean isValidRoom(Long id) {
		Room entity = roomRepository.findById(id).orElse(null);
		if (entity == null) {
			return false;
		}
		return true;
	}

	/**
	 * Validates whether the room is available in the desire booked day
	 * 
	 * @param id - Room id
	 * @return 'OK' if the room is available or a message about the next available
	 *         day
	 */
	public String isRoomAvailable(Long id, List<String> bookedDays) {
		List<BookingCalendar> listBooking = bookingCalendarRepository.findByBookingId(id);

		if (listBooking.isEmpty())
			return "OK";

		String unavailables = "";
		String day = "";
		for (int i = 0; i < listBooking.size(); i++) {
			day = listBooking.get(i).getDay();
			if (bookedDays.contains(day)) {
				unavailables += day + ", ";
			}
		}

		if (unavailables.equals(""))
			return "OK";

		unavailables = unavailables.substring(0, unavailables.length() - 2);

		return "Room is not avaiable amoung the days: " + unavailables;
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
	 * Checks whether the check-in is before today
	 * 
	 * @param checkin
	 * @param checkout
	 * @return true if the check-in is before today
	 */
	public Boolean isCheckinBeforeToday(String checkin) {
		return DateUtils.isCheckinBeforeToday(checkin);
	}

	/**
	 * Compares whether the check-in and check-out date is valid
	 * 
	 * @param checkin
	 * @param checkout
	 * @return true if the check-in is before the check-out or at the same day
	 */
	public Boolean isValidPeriod(BookingDTO dto) {
		return DateUtils.isValidPeriod(dto.getCheckin(), dto.getCheckout());
	}

	/**
	 * Applies all validations
	 * 
	 * @param entity
	 * @return
	 */
	public String validations(BookingDTO dto, List<String> bookedDays) {
		Long roomId = dto.getRoomId();

		if (!isValidRoom(roomId)) {
			return "Room " + roomId + " is not valid!";
		}

		if (!isValidDateFormat(dto.getCheckin(), dto.getCheckout())) {
			return "Please, set the date as dd/MM/yyyy";
		}

		if (isCheckinBeforeToday(dto.getCheckin())) {
			return "Check-in cannot be before today";
		}

		if (!isValidPeriod(dto)) { // verificar se checkout eh antes de check in
			return "Check-in cannot be later than check-out";
		}

		String message = isRoomAvailable(dto.getRoomId(), bookedDays);
		if (!message.equals("OK")) {
			return message;
		}

		return "";
	}

	/**
	 * 
	 * After validations, prepare the booking with the desired booking date to be
	 * inserted on a database
	 * 
	 * @param entity
	 * @param bookedDays
	 * @return
	 */
	public Booking prepareBooking(BookingDTO dto, List<String> bookedDays) {
		Booking booking = BookMapper.parseDTOtoEntity(dto);
		BookingCalendar bookingCalendar;
		List<BookingCalendar> bookingsCalendar = new ArrayList<>();

		for (String bookedDay : bookedDays) {
			bookingCalendar = new BookingCalendar();
			bookingCalendar.setCheckin(dto.getCheckin());
			bookingCalendar.setCheckout(dto.getCheckout());
			bookingCalendar.setDay(bookedDay);
			bookingsCalendar.add(bookingCalendar);
		}

		booking.setBookingsCalendar(bookingsCalendar);

		return booking;
	}

}