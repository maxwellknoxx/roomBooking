package com.challenge.roomBooking.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.challenge.roomBooking.entity.Booking;
import com.challenge.roomBooking.entity.BookingCalendar;
import com.challenge.roomBooking.entity.Room;
import com.challenge.roomBooking.exception.DateException;
import com.challenge.roomBooking.exception.RoomException;
import com.challenge.roomBooking.model.BookingCalendarDTO;
import com.challenge.roomBooking.model.BookingDTO;
import com.challenge.roomBooking.repository.BookingCalendarRepository;
import com.challenge.roomBooking.repository.BookingRepository;
import com.challenge.roomBooking.repository.RoomRepository;
import com.challenge.roomBooking.utils.DateUtils;

@Service
public class ServicesValidation {

	@Autowired
	private RoomRepository roomRepository;

	@Autowired
	private BookingRepository bookingRepository;

	@Autowired
	private BookingCalendarRepository bookingCalendarRepository;

	/**
	 * Creates a list of days between two dates
	 * 
	 * @param dto
	 * @return
	 */
	public List<String> createListOfDays(BookingDTO dto) {
		return DateUtils.createListOfDays(dto);
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
	public String isRoomAvailable(Long roomId, List<String> bookedDays) {
		List<Booking> listBooking = getBookingsByRoomId(roomId);

		String unavailables = "";
		String day = "";

		if (listBooking.isEmpty())
			return "OK";

		List<BookingCalendar> listBookingCalendar = new ArrayList<>();
		for (Booking booking : listBooking) {
			listBookingCalendar = new ArrayList<>();
			listBookingCalendar = getBookingCalendarByBookingId(booking.getId());

			for (int i = 0; i < listBookingCalendar.size(); i++) {
				day = listBookingCalendar.get(i).getDay();
				if (bookedDays.contains(day)) {
					unavailables += day + ", ";
				}
			}
		}

		if (unavailables.equals(""))
			return "OK";

		unavailables = unavailables.substring(0, unavailables.length() - 2);

		return "Room is not available amoung the days: " + unavailables;
	}

	/**
	 * Returns a list of booking by room Id
	 * 
	 * @param roomId
	 * @return
	 */
	public List<Booking> getBookingsByRoomId(Long roomId) {
		return bookingRepository.findByRoomId(roomId);
	}

	/**
	 * Returns a list of Booking Calendar by Booking Id
	 * 
	 * @param bookingId
	 * @return
	 */
	public List<BookingCalendar> getBookingCalendarByBookingId(Long bookingId) {
		return bookingCalendarRepository.findByBookingId(bookingId);
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
	 * Compares whether the check-in and check-out date are valid
	 * 
	 * @param checkin
	 * @param checkout
	 * @return true if the check-in is before the check-out or at the same day
	 */
	public Boolean isValidPeriod(BookingDTO dto) {
		return DateUtils.isValidPeriod(dto.getCheckin(), dto.getCheckout());
	}

	/**
	 * Applies rooms validations
	 * 
	 * @param entity
	 * @return
	 */
	public String roomValidations(Long roomId, List<String> bookedDays) throws RoomException, DateException {
		if (!isValidRoom(roomId)) {
			return "Room " + roomId + " is not valid!";
		}

		String message = isRoomAvailable(roomId, bookedDays);
		if (!message.equals("OK")) {
			return message;
		}

		return "";
	}

	/**
	 * Applies dates validations
	 * 
	 * @param dto
	 * @return
	 */
	public String dateValidation(BookingDTO dto) {
		if (!isValidDateFormat(dto.getCheckin(), dto.getCheckout())) {
			return "Please, set the date as dd/MM/yyyy";
		}

		if (isCheckinBeforeToday(dto.getCheckin())) {
			return "Check-in cannot be before today";
		}

		if (!isValidPeriod(dto)) {
			return "Please, verifiy check-in and/or check-out dates";
		}
		return "";
	}

	/**
	 * Prepares booking to be inserted on database it validates room and dates
	 * 
	 * @param entity
	 * @param bookedDays
	 * @return
	 */
	public BookingDTO prepareBooking(BookingDTO dto) throws RoomException, DateException {
		dto.setDateMessage(dateValidation(dto));

		String dateMessage = dto.getDateMessage();
		if (!dateMessage.equals("")) {
			throw new DateException(Booking.class, dateMessage);
		}

		List<String> bookedDays = createListOfDays(dto);

		dto.setRoomMessage(roomValidations(dto.getRoomId(), bookedDays));

		String roomMessage = dto.getRoomMessage();
		if (!roomMessage.equals("")) {
			throw new RoomException(Room.class, roomMessage);
		}

		BookingCalendarDTO bookingCalendar;
		List<BookingCalendarDTO> bookingsCalendar = new ArrayList<>();

		for (String bookedDay : bookedDays) {
			bookingCalendar = new BookingCalendarDTO();
			bookingCalendar.setCheckin(dto.getCheckin());
			bookingCalendar.setCheckout(dto.getCheckout());
			bookingCalendar.setDay(bookedDay);
			bookingsCalendar.add(bookingCalendar);
		}
		dto.setBookingsCalendar(bookingsCalendar);
		return dto;
	}

}