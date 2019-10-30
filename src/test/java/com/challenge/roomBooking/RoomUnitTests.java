package com.challenge.roomBooking;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.challenge.roomBooking.controller.RoomController;
import com.challenge.roomBooking.entity.Booking;
import com.challenge.roomBooking.entity.Room;
import com.challenge.roomBooking.enums.RoomType;
import com.challenge.roomBooking.model.RoomDTO;
import com.challenge.roomBooking.service.impl.RoomServiceImpl;
import com.challenge.roomBooking.utils.JSONUtils;

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
				.content(JSONUtils.objectToJSON(getRoomEntity()))).andExpect(status().isCreated())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON)).andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void shouldUpdateRoom() throws Exception {

		when(service.save(any(Room.class))).thenReturn(getRoom());

		mockMvc.perform(put("/api/v1/room/rooms").contentType(MediaType.APPLICATION_JSON)
				.content(JSONUtils.objectToJSON(getRoomEntity()))).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON)).andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void shouldFindAll() throws Exception {
		when(service.findAll()).thenReturn(getRooms());

		mockMvc.perform(get("/api/v1/room/rooms").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON)).andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void shouldGetRoomByType() throws Exception {
		when(service.getRoomByType(RoomType.SINGLE)).thenReturn(getRoomsByType());

		mockMvc.perform(post("/api/v1/room/roomsByType").contentType(MediaType.APPLICATION_JSON)
				.content(JSONUtils.objectToJSON(getRoomEntity()))).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON)).andDo(MockMvcResultHandlers.print());
	}

	public RoomDTO getRoom() {
		return RoomDTO.builder().id(1L).type(RoomType.SINGLE).bookings(null).build();
	}

	public List<RoomDTO> getRoomsByType() {
		List<RoomDTO> list = new ArrayList<>();
		list.add(RoomDTO.builder().id(1L).type(RoomType.SINGLE).bookings(null).build());
		list.add(RoomDTO.builder().id(4L).type(RoomType.SINGLE).bookings(null).build());
		list.add(RoomDTO.builder().id(5L).type(RoomType.SINGLE).bookings(null).build());
		return list;
	}

	public List<RoomDTO> getRooms() {
		List<RoomDTO> list = new ArrayList<>();
		list.add(RoomDTO.builder().id(1L).type(RoomType.SINGLE).bookings(null).build());
		list.add(RoomDTO.builder().id(2L).type(RoomType.DOUBLE).bookings(null).build());
		list.add(RoomDTO.builder().id(3L).type(RoomType.SUITE).bookings(null).build());
		return list;
	}

	public Room getRoomEntity() {
		Room entity = new Room();
		entity.setId(1L);
		entity.setRoomType(RoomType.SINGLE);

		Booking booking = new Booking();
		booking.setId(1L);
		booking.setRoom(entity);

		List<Booking> listBooking = new ArrayList<>();
		listBooking.add(booking);

		entity.setBookings(listBooking);

		return entity;
	}

}
