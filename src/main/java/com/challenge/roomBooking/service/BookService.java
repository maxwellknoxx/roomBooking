package com.challenge.roomBooking.service;

import java.util.List;

import com.challenge.roomBooking.entity.BookEntity;
import com.challenge.roomBooking.model.BookModel;

public interface BookService {

	List<BookModel> findAll();
	
	BookModel book(BookEntity entity);
	
	Boolean cancel(BookEntity entity);

}
