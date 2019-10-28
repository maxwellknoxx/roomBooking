package com.challenge.roomBooking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.challenge.roomBooking.entity.Room;
import com.challenge.roomBooking.enums.RoomType;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
	
	List<Room> findAll();
	
	List<Room> findByRoomType(RoomType type);
	
}
