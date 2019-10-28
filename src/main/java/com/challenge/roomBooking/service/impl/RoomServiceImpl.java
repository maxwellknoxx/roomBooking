package com.challenge.roomBooking.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.challenge.roomBooking.entity.Room;
import com.challenge.roomBooking.enums.RoomType;
import com.challenge.roomBooking.model.RoomDTO;
import com.challenge.roomBooking.repository.RoomRepository;
import com.challenge.roomBooking.service.RoomService;
import com.challenge.roomBooking.utils.RoomMapper;

@Service
public class RoomServiceImpl implements RoomService {

	@Autowired
	private RoomRepository repository;

	@Override
	public List<RoomDTO> findAll() {
		List<Room> entities = repository.findAll();
		if (entities.isEmpty()) {
			return null;
		}
		return RoomMapper.getListModel(entities);
	}

	public RoomDTO save(Room entity) {
		Room roomEntity = repository.save(entity);
		if (entity == null) {
			return null;
		}
		return RoomMapper.getModel(roomEntity);
	}

	public RoomDTO update(Room entity) {
		Room roomEntity = repository.save(entity);
		if (entity == null) {
			return null;
		}
		return RoomMapper.getModel(roomEntity);
	}

	public List<RoomDTO> getRoomByType(RoomType type) {
		List<Room> entities = repository.findByRoomType(type);
		if (entities.isEmpty()) {
			return null;
		}
		return RoomMapper.getListModel(entities);
	}

}
