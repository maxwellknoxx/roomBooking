package com.challenge.roomBooking.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.NaturalId;

import com.challenge.roomBooking.enums.RoomType;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Table(name = "rooms")
public class RoomEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Enumerated(EnumType.STRING)
	@NaturalId
	@Column(name = "room_type")
	private RoomType roomType;
	
	@Column(name = "booked_days")
	private List<Integer> bookedDays;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "room")
	private List<BookEntity> books = new ArrayList<>();
	
}
