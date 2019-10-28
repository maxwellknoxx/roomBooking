package com.challenge.roomBooking.model;

import java.util.List;

import com.challenge.roomBooking.enums.RoomType;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class RoomDTO {
	
	private Long id;
	private RoomType type;
	private List<BookingDTO> books;

}
