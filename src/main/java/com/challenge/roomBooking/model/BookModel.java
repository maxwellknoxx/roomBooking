package com.challenge.roomBooking.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class BookModel {
	
	
	private Long id;
	private Long roomId;
	private String bookedDays;

}
