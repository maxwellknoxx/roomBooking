package com.challenge.roomBooking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.challenge.roomBooking.entity.BookingCalendar;

@Repository
public interface BookingCalendarRepository extends JpaRepository<BookingCalendar, Long> {
	
	List<BookingCalendar> findByBookingId(Long id);

}
