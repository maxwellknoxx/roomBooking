package com.challenge.roomBooking.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.challenge.roomBooking.entity.RoomEntity;
import com.challenge.roomBooking.enums.RoomType;
import com.challenge.roomBooking.model.RoomModel;
import com.challenge.roomBooking.repository.RoomRepository;
import com.challenge.roomBooking.service.RoomService;
import com.challenge.roomBooking.utils.RoomMapper;

@Service
public class RoomServiceImpl implements RoomService {

	@Autowired
	private RoomRepository repository;

	@Override
	public List<RoomModel> findAll() {
		List<RoomEntity> entities = repository.findAll();
		if (entities.isEmpty()) {
			return null;
		}
		return RoomMapper.getListModel(entities);
	}

	public RoomModel save(RoomEntity entity) {
		RoomEntity roomEntity = repository.save(entity);
		if (entity == null) {
			return null;
		}
		return RoomMapper.getModel(roomEntity);
	}

	public RoomModel update(RoomEntity entity) {
		RoomEntity roomEntity = repository.save(entity);
		if (entity == null) {
			return null;
		}
		return RoomMapper.getModel(roomEntity);
	}

	public List<RoomModel> getRoomByType(RoomType type) {
		List<RoomEntity> entities = repository.findByRoomType(type);
		if (entities.isEmpty()) {
			return null;
		}
		return RoomMapper.getListModel(entities);
	}

}
