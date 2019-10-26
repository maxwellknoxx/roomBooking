package com.challenge.roomBooking;

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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.challenge.roomBooking.controller.RoomController;
import com.challenge.roomBooking.entity.BookingEntity;
import com.challenge.roomBooking.entity.RoomEntity;
import com.challenge.roomBooking.enums.RoomType;
import com.challenge.roomBooking.model.RoomModel;
import com.challenge.roomBooking.service.impl.RoomServiceImpl;
import com.challenge.roomBooking.utils.DataConverterUtils;

@RunWith(SpringRunner.class)             
@WebMvcTest(RoomController.class) 
class RoomPostUnitTests {

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
		 RequestBuilder request = MockMvcRequestBuilders
		            .post("/api/v1/room/rooms")
		            .accept(MediaType.APPLICATION_JSON)
		            .content("{\n" + 
		            		"	\"id\": \"5\",\n" + 
		            		"	\"roomType\": \"SUITE\",\n" + 
		            		"	\"books\": [{}]\n" + 
		            		"}")
		            .contentType(MediaType.APPLICATION_JSON);

		    MvcResult result = mockMvc.perform(request)
		            .andExpect(status().isCreated())
		            .andReturn();
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
		
		BookingEntity booking = new BookingEntity();
		booking.setId(1L);
		booking.setRoom(entity);
		booking.setCheckin("26/10/2019");
		booking.setCheckout("28/10/2019");
		
		List<BookingEntity> listBooking = new ArrayList<>();
		listBooking.add(booking);
		
		entity.setBooks(listBooking);
		
		return entity;
	}

	public String roomToJSON(RoomEntity entity) {
		return DataConverterUtils.toJSON(entity);
	}

}
