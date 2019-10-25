package com.challenge.roomBooking.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.roomBooking.entity.RoomEntity;
import com.challenge.roomBooking.model.RoomModel;
import com.challenge.roomBooking.service.impl.MapValidationErrorService;
import com.challenge.roomBooking.service.impl.RoomServiceImpl;

@RestController
@RequestMapping("/api/")
public class RoomController {

	@Autowired
	private RoomServiceImpl service;

	@Autowired
	private MapValidationErrorService mapValidationErrorService;

	@GetMapping(path = "v1/room/rooms")
	public ResponseEntity<?> findAll() {
		List<RoomModel> rooms = service.findAll();
		if (rooms == null) {
			return new ResponseEntity<Boolean>(false, HttpStatus.OK);
		}

		return new ResponseEntity<List<RoomModel>>(rooms, HttpStatus.OK);
	}

	@PostMapping(path = "v1/room/rooms")
	public ResponseEntity<?> addRoom(@Valid @RequestBody RoomEntity entity, BindingResult result) {

		ResponseEntity<?> errorMap = mapValidationErrorService.mapValidation(result);
		if (errorMap != null) {
			return errorMap;
		}

		RoomModel room = service.save(entity);
		if (room == null) {
			return new ResponseEntity<Boolean>(false, HttpStatus.OK);
		}

		return new ResponseEntity<RoomModel>(room, HttpStatus.OK);
	}

	@PutMapping(path = "v1/room/rooms")
	public ResponseEntity<?> updateRoom(@Valid @RequestBody RoomEntity entity) {
		RoomModel room = service.update(entity);
		if (room == null) {
			return new ResponseEntity<Boolean>(false, HttpStatus.OK);
		}

		return new ResponseEntity<RoomModel>(room, HttpStatus.OK);
	}

	@PostMapping(path = "v1/room/roomsByType")
	public ResponseEntity<?> getRoomByType(@Valid @RequestBody RoomEntity entity) {
		List<RoomModel> rooms = service.getRoomByType(entity.getRoomType());
		if (rooms == null) {
			return new ResponseEntity<Boolean>(false, HttpStatus.OK);
		}
		return new ResponseEntity<List<RoomModel>>(rooms, HttpStatus.OK);
	}

}
