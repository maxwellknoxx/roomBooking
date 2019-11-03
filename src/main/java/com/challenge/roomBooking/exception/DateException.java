package com.challenge.roomBooking.exception;

import org.apache.commons.lang3.StringUtils;

public class DateException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -814489082846947731L;

	public DateException(Class<?> clazz, String message) {
		super(DateException.generateMessage(clazz.getSimpleName(), message));
	}

	private static String generateMessage(String entity, String message) {
		return StringUtils.capitalize("Message -> " + message);
	}

}
