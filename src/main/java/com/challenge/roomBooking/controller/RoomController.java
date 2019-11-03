package com.challenge.roomBooking.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.roomBooking.entity.Room;
import com.challenge.roomBooking.exception.EntityNotFoundException;
import com.challenge.roomBooking.exception.ResourceNotFoundException;
import com.challenge.roomBooking.model.RoomDTO;
import com.challenge.roomBooking.service.impl.RoomServiceImpl;

@RestController
@RequestMapping("/api/")
public class RoomController {

	@Autowired
	private RoomServiceImpl service;

	@GetMapping(path = "v1/room/rooms")
	public ResponseEntity<?> findAll() throws ResourceNotFoundException {
		List<RoomDTO> rooms = service.findAll();

		return new ResponseEntity<List<RoomDTO>>(rooms, HttpStatus.OK);
	}

	@GetMapping(path = "v1/room/room/{id}")
	public ResponseEntity<?> roomById(@Valid @PathVariable("id") Long id) throws EntityNotFoundException {
		RoomDTO room = service.getRoomById(id);

		return new ResponseEntity<RoomDTO>(room, HttpStatus.OK);
	}

	@PostMapping(path = "v1/room/rooms")
	public ResponseEntity<?> addRoom(@Valid @RequestBody Room entity) {
		RoomDTO room = service.save(entity);

		return new ResponseEntity<RoomDTO>(room, HttpStatus.CREATED);
	}

	@PutMapping(path = "v1/room/rooms")
	public ResponseEntity<?> updateRoom(@Valid @RequestBody Room entity) {
		RoomDTO room = service.update(entity);
		if (room == null) {
			return new ResponseEntity<Boolean>(false, HttpStatus.OK);
		}

		return new ResponseEntity<RoomDTO>(room, HttpStatus.OK);
	}

	@PostMapping(path = "v1/room/roomsByType")
	public ResponseEntity<?> getRoomByType(@Valid @RequestBody Room entity) throws EntityNotFoundException {
		List<RoomDTO> rooms = service.getRoomByType(entity.getRoomType());
		
		return new ResponseEntity<List<RoomDTO>>(rooms, HttpStatus.OK);
	}

}
