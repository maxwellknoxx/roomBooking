package com.challenge.roomBooking.utils;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONUtils {

	public static String objectToJSON(Object object) {
		String jsonString = "";
		try {
			ObjectMapper Obj = new ObjectMapper();
			jsonString = Obj.writeValueAsString(object);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(jsonString);
		return jsonString;
	}

}
