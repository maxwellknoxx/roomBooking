package com.challenge.roomBooking;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
import com.challenge.roomBooking.entity.BookingCalendar;
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

	/*
	 * @Test public void shouldBook() throws Exception {
	 * 
	 * when(servicesValidation.prepareBooking(getBookingDTO())).thenReturn(
	 * getBookingDTO());
	 * when(service.book(getBookingDTO())).thenReturn(getBookingDTO());
	 * 
	 * mockMvc.perform(post("/api/v1/booking/booking").contentType(MediaType.
	 * APPLICATION_JSON)
	 * .content(JSONUtils.objectToJSON(getBookingDTO()))).andExpect(status().
	 * isCreated()) .andExpect(content().contentType(MediaType.APPLICATION_JSON))
	 * .andExpect(MockMvcResultMatchers.content().string(JSONUtils.objectToJSON(
	 * getBookingDTO()))) .andDo(MockMvcResultHandlers.print()); }
	 */

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
		BookingDTO dto = new BookingDTO();
		dto.setRoomId(1L);
		dto.setRoomType(RoomType.SINGLE);
		dto.setCheckin("05/11/2019");
		dto.setCheckout("06//11/2019");
		
		Room roomEntity = new Room();
		roomEntity.setId(1L);
		roomEntity.setRoomType(RoomType.SINGLE);
		
		Booking booking = new Booking();
		booking.setRoom(roomEntity);
		
		List<BookingCalendar> listBc = new ArrayList<>();
		BookingCalendar bc = new BookingCalendar();
		bc.setCheckin("05/11/2019");
		bc.setCheckin("06//11/2019");
		bc.setDay("05/11/2019");
		
		listBc.add(bc);
		booking.setBookingsCalendar(listBc);
		
		dto.setBookingsCalendar(listBc);
		
		return dto;
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
	
	public List<String> getBookedDays(){
		List<String> days = new ArrayList<>();
		days.add("25/11/2019");
		days.add("26/11/2019");
		days.add("27/11/2019");
		return days;
	}

}
