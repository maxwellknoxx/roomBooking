package com.challenge.roomBooking.utils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.challenge.roomBooking.entity.Room;
import com.challenge.roomBooking.model.RoomDTO;

@Component
public class RoomMapper {

	public static RoomDTO getModel(Room entity) {
		return RoomDTO.builder().id(entity.getId()).type(entity.getRoomType())
				.books(BookMapper.getListModel(entity.getBooks())).build();
	}

	public static List<RoomDTO> getListModel(List<Room> entities) {
		return entities
				.stream().filter(Objects::nonNull).map(entity -> RoomDTO.builder().id(entity.getId())
						.type(entity.getRoomType()).books(BookMapper.getListModel(entity.getBooks())).build())
				.collect(Collectors.toList());

	}

}
