package com.challenge.roomBooking;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.challenge.roomBooking.controller.BookingController;
import com.challenge.roomBooking.entity.Booking;
import com.challenge.roomBooking.entity.BookingCalendar;
import com.challenge.roomBooking.entity.Room;
import com.challenge.roomBooking.enums.RoomType;
import com.challenge.roomBooking.model.BookingDTO;
import com.challenge.roomBooking.service.impl.BookingServiceImpl;
import com.challenge.roomBooking.service.impl.ServicesValidation;

@RunWith(SpringRunner.class)
@SpringBootTest
class BookingMethodsUnitTests {

	@Mock
	BookingServiceImpl service;

	@MockBean
	ServicesValidation servicesValidation;

	@InjectMocks
	BookingController controller;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	/*
	 * @Test public void shouldGetBookings() throws Exception {
	 * when(service.findAll()).thenReturn(getBookings());
	 * 
	 * ResponseEntity<?> response = controller.findAll();
	 * 
	 * System.out.println(response.getBody());
	 * 
	 * assertNotNull(response.getBody()); }
	 * 
	 * @Test public void shouldBook() {
	 * 
	 * when(service.book(getBookingDTO())).thenReturn(getBookingDTO());
	 * when(servicesValidation.prepareBooking(getBookingDTO())).thenReturn(
	 * getBookingDTO());
	 * 
	 * ResponseEntity<?> response = controller.booking(getBookingDTO());
	 * 
	 * System.out.println(response.getBody());
	 * 
	 * assertNotNull(response.getBody()); }
	 */
	
	public BookingDTO getBookingDTO() {
		return BookingDTO.builder().id(1L).roomId(1L).roomType(RoomType.SINGLE).checkin("05/11/2019")
				.checkout("10/11/2019").build();
	}

	public Booking getBooking() {
		Booking booking = new Booking();
		booking.setId(1L);

		Room roomEntity = new Room();
		roomEntity.setId(1L);
		roomEntity.setRoomType(RoomType.SINGLE);

		List<BookingCalendar> listBc = new ArrayList<>();
		BookingCalendar bc = new BookingCalendar();
		bc.setCheckin("05/11/2019");
		bc.setCheckin("06//11/2019");
		bc.setDay("05/11/2019");

		listBc.add(bc);
		booking.setBookingsCalendar(listBc);

		booking.setRoom(roomEntity);

		return booking;
	}

	public List<BookingDTO> getBookings() {
		List<BookingDTO> list = new ArrayList<>();
		list.add(BookingDTO.builder().id(1L).roomId(1L).roomType(RoomType.SINGLE).checkin("26/10/2019")
				.checkout("30/10/2019").build());
		list.add(BookingDTO.builder().id(3L).roomId(1L).roomType(RoomType.DOUBLE).checkin("30/11/2019")
				.checkout("05/12/2019").build());
		list.add(BookingDTO.builder().id(2L).roomId(1L).roomType(RoomType.SUITE).checkin("28/10/2019")
				.checkout("30/10/2019").build());
		return list;
	}

	public List<String> getBookedDays() {
		List<String> days = new ArrayList<>();
		days.add("25/11/2019");
		days.add("26/11/2019");
		days.add("27/11/2019");
		return days;
	}

}
