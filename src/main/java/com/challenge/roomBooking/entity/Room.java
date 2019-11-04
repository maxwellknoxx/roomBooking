package com.challenge.roomBooking.entity;

import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.challenge.roomBooking.enums.RoomType;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "rooms")
@Access(AccessType.FIELD)
@EqualsAndHashCode(callSuper = false, exclude = {"bookings"})
@ToString(exclude = {"bookings"})
public class Room {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "room_type")
	private RoomType roomType;
	
	@OneToMany(mappedBy = "room")
	@JsonIgnore
	private List<Booking> bookings;
	
}
