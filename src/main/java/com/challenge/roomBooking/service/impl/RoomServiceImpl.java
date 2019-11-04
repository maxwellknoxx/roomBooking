package com.challenge.roomBooking.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.challenge.roomBooking.entity.Room;
import com.challenge.roomBooking.enums.RoomType;
import com.challenge.roomBooking.exception.EntityNotFoundException;
import com.challenge.roomBooking.exception.ResourceNotFoundException;
import com.challenge.roomBooking.model.RoomDTO;
import com.challenge.roomBooking.repository.RoomRepository;
import com.challenge.roomBooking.service.RoomService;
import com.challenge.roomBooking.utils.RoomMapper;

@Service
public class RoomServiceImpl implements RoomService {

	@Autowired
	private RoomRepository repository;

	@Override
	public List<RoomDTO> findAll() throws ResourceNotFoundException {
		List<Room> entities = repository.findAll();
		if (entities.isEmpty()) {
			throw new ResourceNotFoundException(Room.class, "No room found");
		}
		return RoomMapper.getListDTO(entities);
	}

	public RoomDTO save(Room entity) {
		Room roomEntity = repository.save(entity);
		if (entity == null) {
			return null;
		}
		return RoomMapper.getDTO(roomEntity);
	}

	public RoomDTO update(Room entity) {
		Room roomEntity = repository.save(entity);
		if (entity == null) {
			return null;
		}
		return RoomMapper.getDTO(roomEntity);
	}

	public RoomDTO getRoomById(Long id) throws EntityNotFoundException {
		return RoomMapper.getDTO(repository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(Room.class, "id", id.toString())));
	}

	public List<RoomDTO> getRoomByType(RoomType type) throws EntityNotFoundException {
		List<Room> entities = repository.findByRoomType(type);
		if (entities.isEmpty()) {
			throw new EntityNotFoundException(Room.class, "Type", type.toString());
		}
		return RoomMapper.getListDTO(entities);
	}

}
