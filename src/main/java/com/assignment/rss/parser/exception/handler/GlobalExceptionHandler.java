package com.assignment.rss.parser.exception.handler;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.assignment.rss.parser.exception.custom.InavlidPageNumberException;
import com.assignment.rss.parser.exception.custom.InavlidPageSizeException;
import com.assignment.rss.parser.exception.custom.InvalidSortFieldException;
import com.assignment.rss.parser.exception.custom.InvalidSortingDirection;
import com.assignment.rss.parser.exception.custom.ItemNotFoundException;
import com.assignment.rss.parser.exception.response.ExceptionResponse;

/**
 * The Class GlobalExceptionHandler.
 *
 * @author sachi
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	/**
	 * Handle exceptions.
	 *
	 * @param exception  the exception
	 * @param webRequest the web request
	 * @return the response entity
	 */
	@ExceptionHandler(ItemNotFoundException.class)
	public ResponseEntity<Object> handleExceptions(ItemNotFoundException exception, WebRequest webRequest) {
		ExceptionResponse response = new ExceptionResponse();
		response.setDateTime(LocalDateTime.now());
		response.setMessage("No items found");
		ResponseEntity<Object> entity = new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		return entity;
	}

	/**
	 * Handle exceptions.
	 *
	 * @param exception  the exception
	 * @param webRequest the web request
	 * @return the response entity
	 */
	@ExceptionHandler(InavlidPageNumberException.class)
	public ResponseEntity<Object> handleExceptions(InavlidPageNumberException exception, WebRequest webRequest) {
		ExceptionResponse response = new ExceptionResponse();
		response.setDateTime(LocalDateTime.now());
		response.setMessage("Page number should be greater than zero");
		ResponseEntity<Object> entity = new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		return entity;
	}

	/**
	 * Handle exceptions.
	 *
	 * @param exception  the exception
	 * @param webRequest the web request
	 * @return the response entity
	 */
	@ExceptionHandler(InvalidSortFieldException.class)
	public ResponseEntity<Object> handleExceptions(InvalidSortFieldException exception, WebRequest webRequest) {
		ExceptionResponse response = new ExceptionResponse();
		response.setDateTime(LocalDateTime.now());
		response.setMessage("Unsupported sorting field");
		ResponseEntity<Object> entity = new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		return entity;
	}

	/**
	 * Handle exceptions.
	 *
	 * @param exception  the exception
	 * @param webRequest the web request
	 * @return the response entity
	 */
	@ExceptionHandler(InvalidSortingDirection.class)
	public ResponseEntity<Object> handleExceptions(InvalidSortingDirection exception, WebRequest webRequest) {
		ExceptionResponse response = new ExceptionResponse();
		response.setDateTime(LocalDateTime.now());
		response.setMessage("Unsupported sorting direction. Supported values : [ asc , desc ]");
		ResponseEntity<Object> entity = new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		return entity;
	}

	/**
	 * Handle exceptions.
	 *
	 * @param exception  the exception
	 * @param webRequest the web request
	 * @return the response entity
	 */
	@ExceptionHandler(InavlidPageSizeException.class)
	public ResponseEntity<Object> handleExceptions(InavlidPageSizeException exception, WebRequest webRequest) {
		ExceptionResponse response = new ExceptionResponse();
		response.setDateTime(LocalDateTime.now());
		response.setMessage("Page size should be greater than zero");
		ResponseEntity<Object> entity = new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		return entity;
	}
}
