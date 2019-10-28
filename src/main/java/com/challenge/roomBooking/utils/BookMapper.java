package com.challenge.roomBooking.utils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.challenge.roomBooking.entity.Booking;
import com.challenge.roomBooking.model.BookingDTO;

@Component
public class BookMapper {

	public static BookingDTO getModel(Booking entity) {
		return BookingDTO.builder().id(entity.getId())
				.roomId(entity.getRoom().getId()).checkin(entity.getCheckin())
				.roomType(entity.getRoom().getRoomType())
				.checkout(entity.getCheckout())
				.booked_days(DateUtils.getDatesBetween(DateUtils.convertStringToDate(entity.getCheckin()),
						DateUtils.convertStringToDate(entity.getCheckout())))
				.build();
	}

	public static List<BookingDTO> getListModel(List<Booking> entities) {
		return entities.stream().filter(Objects::nonNull)
				.map(entity -> BookingDTO.builder().id(entity.getId())
						.roomId(entity.getRoom().getId())
						.roomType(entity.getRoom().getRoomType())
						.checkin(entity.getCheckin()).checkout(entity.getCheckout())
						.booked_days(DateUtils.getDatesBetween(DateUtils.convertStringToDate(entity.getCheckin()),
								DateUtils.convertStringToDate(entity.getCheckout())))
						.build())
				.collect(Collectors.toList());
	}

}
