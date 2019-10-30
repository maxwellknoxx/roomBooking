package com.challenge.roomBooking.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.challenge.roomBooking.entity.Booking;
import com.challenge.roomBooking.entity.BookingCalendar;
import com.challenge.roomBooking.entity.Room;
import com.challenge.roomBooking.model.BookingDTO;

@Component
public class BookMapper {

	public static BookingDTO getDTO(Booking entity) {
		return BookingDTO.builder().id(entity.getId()).roomId(entity.getRoom().getId())
				.roomType(entity.getRoom().getRoomType()).checkin(entity.getBookingsCalendar().get(0).getCheckin())
				.checkout(entity.getBookingsCalendar().get(0).getCheckout())
				.bookedDays(DateUtils.getDatesBetween(
						DateUtils.convertStringToDate(entity.getBookingsCalendar().get(0).getCheckin()),
						DateUtils.convertStringToDate(entity.getBookingsCalendar().get(0).getCheckout())))
				.build();
	}

	public static List<BookingDTO> getListDTO(List<Booking> entities) {
		return entities.stream().filter(Objects::nonNull)
				.map(entity -> BookingDTO.builder().id(entity.getId()).roomId(entity.getRoom().getId())
						.roomType(entity.getRoom().getRoomType())
						.checkin(entity.getBookingsCalendar().get(0).getCheckin())
						.checkout(entity.getBookingsCalendar().get(0).getCheckout())
						.bookedDays(DateUtils.getDatesBetween(
								DateUtils.convertStringToDate(entity.getBookingsCalendar().get(0).getCheckin()),
								DateUtils.convertStringToDate(entity.getBookingsCalendar().get(0).getCheckout())))
						.build())
				.collect(Collectors.toList());
	}

	public static Booking parseDTOtoEntity(BookingDTO dto) {
		Booking booking = new Booking();
		Room room = new Room();

		room.setId(dto.getRoomId());
		room.setRoomType(dto.getRoomType());

		booking.setRoom(room);
		
		List<BookingCalendar> listBC = new ArrayList<>();
		listBC = dto.getBookingsCalendar();
		
		booking.setBookingsCalendar(listBC);

		return booking;
	}

}
