package com.challenge.roomBooking.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.roomBooking.entity.BookEntity;
import com.challenge.roomBooking.model.BookModel;
import com.challenge.roomBooking.service.impl.BookServiceImpl;
import com.challenge.roomBooking.service.impl.MapValidationErrorService;

@RestController
@RequestMapping("/api/")
public class BookController {

	@Autowired
	private BookServiceImpl service;

	@Autowired
	private MapValidationErrorService mapValidationErrorService;

	@PostMapping(path = "v1/book/books")
	public ResponseEntity<?> book(@Valid @RequestBody BookEntity entity, BindingResult result) {

		ResponseEntity<?> errorMap = mapValidationErrorService.mapValidation(result);
		if (errorMap != null) {
			return errorMap;
		}

		BookModel book = service.book(entity);
		if (book == null) {
			return new ResponseEntity<Boolean>(false, HttpStatus.OK);
		}
		return new ResponseEntity<BookModel>(book, HttpStatus.OK);
	}

	@GetMapping(path = "v1/book/books")
	public ResponseEntity<?> findAll() {
		List<BookModel> books = service.findAll();
		if (books == null) {
			return new ResponseEntity<Boolean>(false, HttpStatus.OK);
		}
		return new ResponseEntity<List<BookModel>>(books, HttpStatus.OK);
	}

	@DeleteMapping(path = "v1/book/books/{id}")
	public ResponseEntity<?> cancel(@Valid @PathVariable("id") Long id) {
		BookEntity book = service.getBookById(id);
		if (book == null) {
			return new ResponseEntity<Boolean>(false, HttpStatus.OK);
		}
		if (service.cancel(book)) {
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		}
		return new ResponseEntity<Boolean>(false, HttpStatus.OK);
	}

	@PutMapping(path = "v1/book/books")
	public ResponseEntity<?> updateBook(@Valid @RequestBody BookEntity entity) {
		BookModel book = service.updateBook(entity);
		if (book == null) {
			return new ResponseEntity<Boolean>(false, HttpStatus.OK);
		}
		return new ResponseEntity<BookModel>(book, HttpStatus.OK);
	}

}
