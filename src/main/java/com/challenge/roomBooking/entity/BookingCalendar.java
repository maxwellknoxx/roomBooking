package com.challenge.roomBooking.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "booking_calendar")
public class BookingCalendar {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "booking_id")
	private Booking booking;

	@Column(name = "check_in")
	private String checkin;
	
	@Column(name = "check_out")
	private String checkout;
	
	@Column(name = "day")
	private String day;

}
