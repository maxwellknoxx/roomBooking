package com.challenge.roomBooking;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;

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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.challenge.roomBooking.controller.BookingController;
import com.challenge.roomBooking.entity.BookingEntity;
import com.challenge.roomBooking.entity.RoomEntity;
import com.challenge.roomBooking.enums.RoomType;
import com.challenge.roomBooking.model.BookingModel;
import com.challenge.roomBooking.service.impl.BookingServiceImpl;
import com.challenge.roomBooking.service.impl.ServicesValidation;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(BookingController.class)
class BookingPostUnitTests {

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

		when(service.book(any(BookingEntity.class))).thenReturn(getBooking());
		when(servicesValidation.isValidRoom(1L)).thenReturn(true);
		when(servicesValidation.isValidPeriod(getBookingEntity())).thenReturn(true);
		when(servicesValidation.isValidDateFormat("05/11/2019", "10/11/2019")).thenReturn(true);
		when(servicesValidation.isRoomAvailable(1L, "05/11/2019")).thenReturn("OK");

		mockMvc.perform(post("/api/v1/booking/booking").contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(objectoToJson(getBookingEntity()))).andExpect(status().isCreated())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON));

	}

	public BookingModel getBooking() {
		return BookingModel.builder().id(1L).roomId(1L).roomType(RoomType.SINGLE).checkin("05/11/2019")
				.checkout("10/11/2019").booked_days(null).build();
	}

	public BookingEntity getBookingEntity() {
		BookingEntity booking = new BookingEntity();
		booking.setId(1L);
		booking.setCheckin("05/11/2019");
		booking.setCheckout("10/11/2019");

		RoomEntity roomEntity = new RoomEntity();
		roomEntity.setId(1L);
		roomEntity.setRoomType(RoomType.SINGLE);

		booking.setRoom(roomEntity);

		return booking;
	}
	
	public String objectoToJson(BookingEntity entity) {
		String jsonStr = "";
		try {
			ObjectMapper Obj = new ObjectMapper();
			jsonStr = Obj.writeValueAsString(entity);

			System.out.println(jsonStr);
		}

		catch (IOException e) {
			e.printStackTrace();
		}
		return jsonStr;

	}

}
