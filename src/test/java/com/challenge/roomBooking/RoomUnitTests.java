package com.challenge.roomBooking;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.challenge.roomBooking.controller.RoomController;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class RoomUnitTests {

	@InjectMocks
	private RoomController roomController;

	@Autowired
	private MockMvc mockMvc;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(roomController).build();
	}

	@Test
	public void shouldReturnStatusOK() throws Exception {
		this.mockMvc.perform(get("/api/v1/room/rooms")).andExpect(status().isOk())
				.andExpect(content().contentType("application/json"));
	}

	/*
	 * @Test public void shouldReturnRoomByType() throws Exception { RoomEntity
	 * entity = new RoomEntity(); entity.setId(1L);
	 * entity.setRoomType(RoomType.SINGLE);
	 * 
	 * mockMvc.perform(post("/api/v1/room/roomsByType",
	 * entity)).andExpect(status().isOk()); }
	 */
	@Test
	void contextLoads() {
	}

}
