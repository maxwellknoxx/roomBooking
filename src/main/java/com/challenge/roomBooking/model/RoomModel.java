package com.challenge.roomBooking.model;

import com.challenge.roomBooking.enums.RoomType;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class RoomModel {
	
	private Long id;
	private RoomType type;
	private String bookedDays;

}
