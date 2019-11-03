package com.challenge.roomBooking.exception;

import org.apache.commons.lang3.StringUtils;

public class ResourceNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3274693183475519791L;

	public ResourceNotFoundException(Class<?> clazz, String message) {
		super(ResourceNotFoundException.generateMessage(clazz.getSimpleName(), message));
	}

	private static String generateMessage(String entity, String message) {
		return StringUtils.capitalize(entity + " " + message);
	}

}
