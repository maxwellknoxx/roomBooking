package com.challenge.roomBooking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.challenge.roomBooking.entity.BookingEntity;

@Repository
public interface BookingRepository extends JpaRepository<BookingEntity, Long> {

	List<BookingEntity> findAll();
	
	List<BookingEntity> findByRoomId(Long id);
	
	BookingEntity findByRoomIdAndCheckin(Long id, String checkin);

}
