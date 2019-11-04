package com.challenge.roomBooking;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
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
import com.challenge.roomBooking.model.BookingCalendarDTO;
import com.challenge.roomBooking.model.BookingDTO;
import com.challenge.roomBooking.service.impl.BookingCalendarServiceImpl;
import com.challenge.roomBooking.service.impl.BookingServiceImpl;
import com.challenge.roomBooking.service.impl.ServicesValidation;
import com.challenge.roomBooking.utils.DateUtils;
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

	@MockBean
	BookingCalendarServiceImpl bookingCalendarService;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		BookingController controller = new BookingController();
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	@Test
	public void shouldCancelBook() throws Exception {

		when(service.getBookingById(1L)).thenReturn(getBooking());
		when(service.cancel(1L)).thenReturn(true);

		mockMvc.perform(delete("/api/v1/booking/booking/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentType("text/plain;charset=UTF-8"))
				.andExpect(MockMvcResultMatchers.content().string("Booking 1 cancelled"))
				.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void shouldFindAll() throws Exception {
		when(service.findAll()).thenReturn(getBookings());

		mockMvc.perform(get("/api/v1/booking/bookings").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void shouldGetBookingById() throws Exception {
		when(service.getBookingDTOById(1L)).thenReturn(getResponseBookingDTO());

		mockMvc.perform(get("/api/v1/booking/booking/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.content().string(getResponseBookingDTOJSON()))
				.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void shouldBook() throws Exception {

		when(service.book(any(BookingDTO.class))).thenReturn(getResponseBookingDTO());

		mockMvc.perform(post("/api/v1/booking/booking").characterEncoding("utf-8")
				.contentType(MediaType.APPLICATION_JSON).content(JSONUtils.objectToJSON(getRequestBookingDTO())))
				.andExpect(status().isCreated())

				.andExpect(content().contentType("text/plain;charset=UTF-8"))
				.andExpect(
						MockMvcResultMatchers.content().string(roomBookedMessage(1L, 1L, "25/11/2019", "27/11/2019")))
				.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void shouldUpdate() throws Exception {

		when(service.book(any(BookingDTO.class))).thenReturn(getResponseUpdateBookingDTO());

		mockMvc.perform(put("/api/v1/booking/booking").characterEncoding("utf-8")
				.contentType(MediaType.APPLICATION_JSON).content(JSONUtils.objectToJSON(getRequestBookingDTO())))
				.andExpect(status().isOk())

				.andExpect(content().contentType("text/plain;charset=UTF-8"))
				.andExpect(MockMvcResultMatchers.content()
						.string(roomBookedUpdatedMessage(2L, 1L, "26/11/2019", "29/11/2019")))
				.andDo(MockMvcResultHandlers.print());
	}

	public String getResponseBookingDTOJSON() {
		return "{\"id\":1,\"roomId\":1,\"roomType\":\"SINGLE\",\"checkin\":\"25/11/2019\",\"checkout\":\"27/11/2019\",\"bookedDays\":[\"2019-11-25\",\"2019-11-26\",\"2019-11-27\"]}";
	}

	public BookingDTO getRequestBookingDTO() {
		BookingDTO bookingDTO = new BookingDTO();

		bookingDTO.setCheckin("25/11/2019");
		bookingDTO.setCheckout("27/11/2019");

		bookingDTO.setRoomId(1L);
		bookingDTO.setRoomType(RoomType.SINGLE);

		return bookingDTO;
	}

	public BookingDTO getResponseBookingDTO() {
		BookingDTO bookingDTO = new BookingDTO();
		BookingCalendarDTO bookingCalendarDTO = new BookingCalendarDTO();
		List<BookingCalendarDTO> bookingsCalendarDTO = new ArrayList<>();

		bookingDTO.setId(1L);
		bookingDTO.setCheckin("25/11/2019");
		bookingDTO.setCheckout("27/11/2019");

		bookingDTO.setBookedDays(getDatesBetween());

		bookingDTO.setRoomId(1L);
		bookingDTO.setRoomType(RoomType.SINGLE);

		bookingCalendarDTO.setId(1L);
		bookingCalendarDTO.setBookingId(1L);

		bookingsCalendarDTO.add(bookingCalendarDTO);

		bookingDTO.setBookingsCalendar(bookingsCalendarDTO);

		return bookingDTO;
	}

	public BookingDTO getResponseUpdateBookingDTO() {
		BookingDTO bookingDTO = new BookingDTO();
		BookingCalendarDTO bookingCalendarDTO = new BookingCalendarDTO();
		List<BookingCalendarDTO> bookingsCalendarDTO = new ArrayList<>();

		bookingDTO.setId(2L);
		bookingDTO.setCheckin("26/11/2019");
		bookingDTO.setCheckout("29/11/2019");

		bookingDTO.setBookedDays(getDatesBetween());

		bookingDTO.setRoomId(1L);
		bookingDTO.setRoomType(RoomType.SINGLE);

		bookingCalendarDTO.setId(2L);
		bookingCalendarDTO.setBookingId(2L);

		bookingsCalendarDTO.add(bookingCalendarDTO);

		bookingDTO.setBookingsCalendar(bookingsCalendarDTO);

		return bookingDTO;
	}

	public Booking getBooking() {
		Booking booking = new Booking();
		booking.setId(1L);

		Room roomEntity = new Room();
		roomEntity.setId(1L);
		roomEntity.setRoomType(RoomType.SINGLE);

		booking.setRoom(roomEntity);

		List<BookingCalendar> listBc = new ArrayList<>();

		BookingCalendar bc1 = new BookingCalendar();
		bc1.setCheckin("25/11/2019");
		bc1.setCheckin("27/11/2019");
		bc1.setDay("2019-11-25");

		BookingCalendar bc2 = new BookingCalendar();
		bc2.setCheckin("25/11/2019");
		bc2.setCheckin("27/11/2019");
		bc2.setDay("2019-11-26");

		BookingCalendar bc3 = new BookingCalendar();
		bc3.setCheckin("25/11/2019");
		bc3.setCheckin("27/11/2019");
		bc3.setDay("2019-11-27");

		listBc.add(bc1);
		listBc.add(bc2);
		listBc.add(bc3);
		booking.setBookingsCalendar(listBc);

		return booking;
	}

	public List<BookingDTO> getBookings() {
		List<BookingDTO> list = new ArrayList<>();
		list.add(BookingDTO.builder().id(1L).roomId(1L).roomType(RoomType.SINGLE).checkin("25/11/2019")
				.checkout("27/11/2019").build());
		list.add(BookingDTO.builder().id(3L).roomId(2L).roomType(RoomType.DOUBLE).checkin("30/11/2019")
				.checkout("05/12/2019").build());
		list.add(BookingDTO.builder().id(2L).roomId(3L).roomType(RoomType.SUITE).checkin("28/10/2019")
				.checkout("30/10/2019").build());
		return list;
	}

	public List<String> getBookedDays() {
		List<String> days = new ArrayList<>();
		days.add("2019-11-25");
		days.add("2019-11-26");
		days.add("2019-11-27");
		return days;
	}

	public List<LocalDate> getDatesBetween() {
		return DateUtils.getDatesBetween(DateUtils.convertStringToDate("25/11/2019"),
				DateUtils.convertStringToDate("27/11/2019"));
	}

	private String roomBookedMessage(Long bookingId, Long roomId, String checkin, String checkout) {
		return "Booking number: " + bookingId + " \nRoom: " + roomId + " \nReserve from: " + checkin + " to "
				+ checkout;
	}

	private String roomBookedUpdatedMessage(Long bookingId, Long roomId, String checkin, String checkout) {
		return "Booking number updated: " + bookingId + " \nRoom: " + roomId + " \nReserve from: " + checkin + " to "
				+ checkout;
	}

}
