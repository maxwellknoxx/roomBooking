package com.challenge.roomBooking;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.challenge.roomBooking.controller.BookingController;
import com.challenge.roomBooking.entity.Booking;
import com.challenge.roomBooking.entity.Room;
import com.challenge.roomBooking.enums.RoomType;
import com.challenge.roomBooking.model.BookingDTO;
import com.challenge.roomBooking.service.impl.BookingServiceImpl;
import com.challenge.roomBooking.service.impl.ServicesValidation;
import com.challenge.roomBooking.utils.JSONUtils;

@RunWith(SpringRunner.class)
@WebMvcTest(BookingController.class)
class BookingUnitTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	BookingServiceImpl service;

	@MockBean
	ServicesValidation servicesValidation;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		BookingController controller = new BookingController();
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	@Test
	public void shouldBook() throws Exception {

		when(service.book(any(Booking.class))).thenReturn(getBookingDTO());
		when(servicesValidation.validations(any(Booking.class))).thenReturn("");

		mockMvc.perform(post("/api/v1/booking/booking").contentType(MediaType.APPLICATION_JSON)
				.content(JSONUtils.objectToJSON(getBooking()))).andExpect(status().isCreated())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.content().string(JSONUtils.objectToJSON(getBookingDTO())))
				.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void shouldUpdateBook() throws Exception {

		when(service.updateBooking(any(Booking.class))).thenReturn(getBookingDTO());

		mockMvc.perform(put("/api/v1/booking/bookings").contentType(MediaType.APPLICATION_JSON)
				.content(JSONUtils.objectToJSON(getBooking()))).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.content().string(JSONUtils.objectToJSON(getBookingDTO())))
				.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void shouldCancelBook() throws Exception {

		when(service.getBookingById(1L)).thenReturn(getBooking());
		when(service.cancel(1L)).thenReturn(true);

		mockMvc.perform(delete("/api/v1/booking/bookings/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.content().string("true")).andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void shouldGetBookingById() throws Exception {
		when(service.getBookingDTOById(1L)).thenReturn(getBookingDTO());

		mockMvc.perform(get("/api/v1/booking/bookingById/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.content().string(JSONUtils.objectToJSON(getBookingDTO())))
				.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void shouldFindAll() throws Exception {
		when(service.findAll()).thenReturn(getBookings());

		mockMvc.perform(get("/api/v1/booking/bookings").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andDo(MockMvcResultHandlers.print());

	}

	public BookingDTO getBookingDTO() {
		return BookingDTO.builder().id(1L).roomId(1L).roomType(RoomType.SINGLE).checkin("05/11/2019")
				.checkout("10/11/2019").booked_days(null).build();
	}

	public Booking getBooking() {
		Booking booking = new Booking();
		booking.setId(1L);
		booking.setCheckin("05/11/2019");
		booking.setCheckout("10/11/2019");

		Room roomEntity = new Room();
		roomEntity.setId(1L);
		roomEntity.setRoomType(RoomType.SINGLE);

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

}
