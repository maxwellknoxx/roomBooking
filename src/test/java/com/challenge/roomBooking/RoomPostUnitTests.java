package com.challenge.roomBooking;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.challenge.roomBooking.controller.RoomController;
import com.challenge.roomBooking.entity.RoomEntity;
import com.challenge.roomBooking.enums.RoomType;
import com.challenge.roomBooking.model.RoomModel;
import com.challenge.roomBooking.service.impl.RoomServiceImpl;
import com.challenge.roomBooking.utils.DataConverterUtils;

@RunWith(SpringRunner.class)
@WebMvcTest(RoomController.class)
class RoomPostUnitTests {

	private MockMvc mockMvc;

	@Mock
	RoomServiceImpl service;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		RoomController controller = new RoomController();
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	@Test
	public void shouldAddRoom() throws Exception {
		when(service.save(Mockito.any(RoomEntity.class))).thenReturn(getRoom());

		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/room/rooms")
                .content(roomToJSON(getRoomEntity()))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));
	}
	
	@Test
	public void shouldReturnStatusOK() throws Exception {
		this.mockMvc.perform(get("/api/v1/room/rooms")).andExpect(status().isOk())
				.andExpect(content().contentType("application/json"));
	}


	public RoomModel getRoom() {
		return RoomModel.builder().id(1L).type(RoomType.SINGLE).books(null).build();
	}

	public RoomEntity getRoomEntity() {
		RoomEntity entity = new RoomEntity();
		entity.setId(1L);
		entity.setRoomType(RoomType.SINGLE);
		return entity;
	}

	public String roomToJSON(RoomEntity entity) {
		return DataConverterUtils.toJSON(entity);
	}

}
