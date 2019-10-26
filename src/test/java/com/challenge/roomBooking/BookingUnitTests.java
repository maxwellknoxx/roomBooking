package com.challenge.roomBooking;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.challenge.roomBooking.controller.BookingController;
import com.challenge.roomBooking.enums.RoomType;
import com.challenge.roomBooking.model.BookingModel;
import com.challenge.roomBooking.service.impl.BookingServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
class BookingUnitTests {

	@Mock
	BookingServiceImpl service;

	@InjectMocks
	BookingController controller;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void shouldGetBookings() throws Exception {
		when(service.findAll()).thenReturn(getBookings());

		ResponseEntity<?> response = controller.findAll();

		assertNotNull(response);

		@SuppressWarnings("unchecked")
		List<BookingModel> bookings = (List<BookingModel>) response.getBody();

		assertEquals(bookings.size(), getBookings().size());
	}

	public List<BookingModel> getBookings() {
		List<BookingModel> list = new ArrayList<>();
		list.add(BookingModel.builder().id(1L).roomId(1L).roomType(RoomType.SINGLE).checkin("26/10/2019")
				.checkout("30/10/2019").build());
		list.add(BookingModel.builder().id(3L).roomId(1L).roomType(RoomType.DOUBLE).checkin("30/11/2019")
				.checkout("05/12/2019").build());
		list.add(BookingModel.builder().id(2L).roomId(1L).roomType(RoomType.SUITE).checkin("28/10/2019")
				.checkout("30/10/2019").build());
		return list;
	}

}
