package com.challenge.roomBooking.utils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.challenge.roomBooking.entity.RoomEntity;
import com.challenge.roomBooking.model.RoomModel;

@Component
public class RoomMapper {

	public static RoomModel getModel(RoomEntity entity) {
		return RoomModel.builder().id(entity.getId()).type(entity.getRoomType()).bookedDays(entity.getBookedDays())
				.build();
	}

	public static List<RoomModel> getListModel(List<RoomEntity> entities) {
		return entities
				.stream().filter(Objects::nonNull).map(entity -> RoomModel.builder().id(entity.getId())
						.type(entity.getRoomType()).bookedDays(entity.getBookedDays()).build())
				.collect(Collectors.toList());

	}

}
