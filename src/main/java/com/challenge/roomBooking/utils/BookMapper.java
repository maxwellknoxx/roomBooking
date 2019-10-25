package com.challenge.roomBooking.utils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.challenge.roomBooking.entity.BookingEntity;
import com.challenge.roomBooking.model.BookingModel;

@Component
public class BookMapper {

	public static BookingModel getModel(BookingEntity entity) {
		return BookingModel.builder().id(entity.getId())
				.roomId(entity.getRoom().getId()).checkin(entity.getCheckin())
				.roomType(entity.getRoom().getRoomType())
				.checkout(entity.getCheckout())
				.booked_days(DateUtils.getDatesBetween(DateUtils.convertStringToDate(entity.getCheckin()),
						DateUtils.convertStringToDate(entity.getCheckout())))
				.build();
	}

	public static List<BookingModel> getListModel(List<BookingEntity> entities) {
		return entities.stream().filter(Objects::nonNull)
				.map(entity -> BookingModel.builder().id(entity.getId())
						.roomId(entity.getRoom().getId())
						.roomType(entity.getRoom().getRoomType())
						.checkin(entity.getCheckin()).checkout(entity.getCheckout())
						.booked_days(DateUtils.getDatesBetween(DateUtils.convertStringToDate(entity.getCheckin()),
								DateUtils.convertStringToDate(entity.getCheckout())))
						.build())
				.collect(Collectors.toList());
	}

}
