package com.challenge.roomBooking.utils;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.challenge.roomBooking.entity.RoomEntity;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class DataConverterUtils {

	public static String toJSON(RoomEntity entity) {
		ObjectMapper Obj = new ObjectMapper();

		try {
			String jsonStr = Obj.writeValueAsString(entity);

			System.out.println(jsonStr);
		}

		catch (IOException e) {
			e.printStackTrace();
		}

		return "";
	}

}
