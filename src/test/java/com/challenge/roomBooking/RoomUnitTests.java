package com.challenge.roomBooking;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.challenge.roomBooking.controller.RoomController;
import com.challenge.roomBooking.entity.Booking;
import com.challenge.roomBooking.entity.Room;
import com.challenge.roomBooking.enums.RoomType;
import com.challenge.roomBooking.model.RoomDTO;
import com.challenge.roomBooking.service.impl.RoomServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(RoomController.class)
class RoomUnitTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	RoomServiceImpl service;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		RoomController controller = new RoomController();
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	@Test
	public void shouldAddRoom() throws Exception {

		when(service.save(any(Room.class))).thenReturn(getRoom());

		mockMvc.perform(post("/api/v1/room/rooms").contentType(MediaType.APPLICATION_JSON)
				.content(objectoToJson(getRoomEntity()))).andExpect(status().isCreated())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON));

	}

	public RoomDTO getRoom() {
		return RoomDTO.builder().id(1L).type(RoomType.SINGLE).books(null).build();
	}

	public Room getRoomEntity() {
		Room entity = new Room();
		entity.setId(1L);
		entity.setRoomType(RoomType.SINGLE);

		Booking booking = new Booking();
		booking.setId(1L);
		booking.setRoom(entity);
		booking.setCheckin("26/10/2019");
		booking.setCheckout("28/10/2019");

		List<Booking> listBooking = new ArrayList<>();
		listBooking.add(booking);

		entity.setBooks(listBooking);

		return entity;
	}

	public String objectoToJson(Room entity) {
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
