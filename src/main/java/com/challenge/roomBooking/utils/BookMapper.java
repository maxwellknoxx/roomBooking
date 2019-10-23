package com.challenge.roomBooking.utils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.challenge.roomBooking.entity.BookEntity;
import com.challenge.roomBooking.model.BookModel;

@Component
public class BookMapper {

	public static BookModel getModel(BookEntity entity) {
		return BookModel.builder().id(entity.getId()).roomId(entity.getRoom().getId())
				.bookedDays(entity.getBookedDays()).build();
	}

	public static List<BookModel> getListModel(List<BookEntity> entities) {
		return entities
				.stream().filter(Objects::nonNull).map(entity -> BookModel.builder().id(entity.getId())
						.roomId(entity.getRoom().getId()).bookedDays(entity.getBookedDays()).build())
				.collect(Collectors.toList());
	}

}
