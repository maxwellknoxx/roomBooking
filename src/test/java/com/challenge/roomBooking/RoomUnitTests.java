package com.challenge.roomBooking;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.challenge.roomBooking.controller.RoomController;
import com.challenge.roomBooking.entity.RoomEntity;
import com.challenge.roomBooking.enums.RoomType;
import com.challenge.roomBooking.model.RoomModel;
import com.challenge.roomBooking.service.impl.RoomServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
class RoomUnitTests {

	@InjectMocks
	RoomController roomController;

	@Mock
	RoomServiceImpl service;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	
	@Test
	public void shouldGetRooms() throws Exception {
		when(service.findAll()).thenReturn(getRooms());

		ResponseEntity<?> response = roomController.findAll();

		assertNotNull(response);
		
		@SuppressWarnings("unchecked")
		List<RoomModel> rooms = (List<RoomModel>) response.getBody();

		assertEquals(rooms.size(), getRooms().size());
	}
	
	@Test
	public void shouldGetRoomByType() {
		when(service.getRoomByType(RoomType.SINGLE)).thenReturn(getRoomsByType());
		
		ResponseEntity<?> response = roomController.getRoomByType(getRoomEntity());

		assertNotNull(response);
		
		@SuppressWarnings("unchecked")
		List<RoomModel> rooms = (List<RoomModel>) response.getBody();

		assertEquals(RoomType.SINGLE, rooms.get(0).getType());
	}

	public List<RoomModel> getRooms() {
		List<RoomModel> list = new ArrayList<>();
		list.add(RoomModel.builder().id(1L).type(RoomType.SINGLE).books(null).build());
		list.add(RoomModel.builder().id(2L).type(RoomType.DOUBLE).books(null).build());
		list.add(RoomModel.builder().id(3L).type(RoomType.SUITE).books(null).build());
		return list;
	}
	
	public List<RoomModel> getRoomsByType() {
		List<RoomModel> list = new ArrayList<>();
		list.add(RoomModel.builder().id(1L).type(RoomType.SINGLE).books(null).build());
		list.add(RoomModel.builder().id(4L).type(RoomType.SINGLE).books(null).build());
		list.add(RoomModel.builder().id(5L).type(RoomType.SINGLE).books(null).build());
		return list;
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
	
	@Test
	void contextLoads() {
	}

}
