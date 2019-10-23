package com.challenge.roomBooking.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.challenge.roomBooking.entity.BookEntity;
import com.challenge.roomBooking.model.BookModel;
import com.challenge.roomBooking.repository.BookRepository;
import com.challenge.roomBooking.service.BookService;
import com.challenge.roomBooking.utils.BookMapper;

@Service
public class BookServiceImpl implements BookService {

	@Autowired
	private BookRepository repository;

	@Override
	public List<BookModel> findAll() {
		List<BookEntity> entities = repository.findAll();
		if (entities.isEmpty()) {
			return null;
		}
		return BookMapper.getListModel(entities);
	}

	@Override
	public BookModel book(BookEntity entity) {
		BookEntity bookEntity = repository.save(entity);
		if (bookEntity == null) {
			return null;
		}
		return BookMapper.getModel(bookEntity);
	}

	@Override
	public Boolean cancel(BookEntity entity) {
		try {
			repository.delete(entity);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public BookEntity getBookById(Long id) {
		BookEntity entity = repository.findById(id).orElse(null);
		if(entity == null) {
			return null;
		}
		return entity;
	}
	
	public BookModel updateBook(BookEntity entity) {
		BookEntity bookEntity = repository.save(entity);
		if (bookEntity == null) {
			return null;
		}
		return BookMapper.getModel(bookEntity);
	}

}
