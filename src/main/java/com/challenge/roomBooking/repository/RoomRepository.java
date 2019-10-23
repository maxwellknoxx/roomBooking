package com.challenge.roomBooking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.challenge.roomBooking.entity.RoomEntity;
import com.challenge.roomBooking.enums.RoomType;

@Repository
public interface RoomRepository extends JpaRepository<RoomEntity, Long> {
	
	List<RoomEntity> findAll();
	
	List<RoomEntity> findByRoomType(RoomType type);
	
}
