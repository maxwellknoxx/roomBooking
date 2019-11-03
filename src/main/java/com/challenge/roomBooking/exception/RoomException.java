package com.challenge.roomBooking.exception;

import org.apache.commons.lang3.StringUtils;

public class RoomException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -814489082846947731L;

	public RoomException(Class<?> clazz, String message) {
		super(RoomException.generateMessage(clazz.getSimpleName(), message));
	}

	private static String generateMessage(String entity, String message) {
		return StringUtils.capitalize("Message -> " + message);
	}

}
