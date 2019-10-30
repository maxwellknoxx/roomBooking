package com.challenge.roomBooking.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.challenge.roomBooking.model.BookingDTO;

public class DateUtils {

	/**
	 * Validates whether the date is in the format ( dd/MM/yyyy )
	 * 
	 * @param dateStr
	 * @return true if it is in the format dd/MM/yyyy
	 */
	public static Boolean isValidDateFormat(String stringDate) {
		DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		sdf.setLenient(false);
		try {
			sdf.parse(stringDate);
		} catch (ParseException e) {
			return false;
		}
		return true;
	}

	/**
	 * Converts a String to Date
	 * 
	 * @param stringDate
	 * @return
	 * @throws ParseException
	 */
	public static Date convertStringToDate(String stringDate) {
		Date date = null;
		try {
			date = new SimpleDateFormat("dd/MM/yyyy").parse(stringDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * Checks whether checkin is befor checkout
	 * 
	 * @param checkin
	 * @param checkout
	 * @return true if the check-in is before the check-out or at the same day
	 */
	public static Boolean isValidCheckin(String checkin, String checkout) {
		Date dateCheckIn = convertStringToDate(checkin);
		Date dateCheckOut = convertStringToDate(checkout);

		int output = dateCheckIn.compareTo(dateCheckOut);
		if (output == -1 || output == 0) {
			return true;
		}

		return false;
	}

	/**
	 * Checks whether checkout is after checkin
	 * 
	 * @param checkin
	 * @param checkout
	 * @return 
	 */
	public static Boolean isValidCheckout(String checkin, String checkout) {
		Date dateCheckIn = convertStringToDate(checkin);
		Date dateCheckOut = convertStringToDate(checkout);

		int output = dateCheckOut.compareTo(dateCheckIn);
		if (output == 1 || output == 0) {
			return true;
		}

		return false;
	}

	public static Boolean isValidPeriod(String checkin, String checkout) {
		return isValidCheckin(checkin, checkout)
				|| isValidCheckout(checkin, checkout);
	}

	/**
	 * Checks whether the check-in is before today
	 * 
	 * @param checkin
	 * @param checkout
	 * @return true if the check-in is before today
	 */
	public static Boolean isCheckinBeforeToday(String checkin) {
		Date dateCheckIn = convertStringToDate(checkin);
		Date dateCheckOut = convertStringToDate(getToday());

		int output = dateCheckIn.compareTo(dateCheckOut);
		if (output == -1) {
			return true;
		}

		return false;
	}
	
	/**
	 * Returns all days between two dates
	 * 
	 * @param checkin
	 * @param checkout
	 * @return List with the days between two dates
	 */
	public static List<LocalDate> getDatesBetween(Date checkin, Date checkout) {
		LocalDate startDate = convertToLocalDate(checkin);
		LocalDate endDate = convertToLocalDate(checkout);
		long numOfDaysBetween = ChronoUnit.DAYS.between(startDate, endDate);
		return IntStream.iterate(0, i -> i + 1).limit(numOfDaysBetween + 1).mapToObj(i -> startDate.plusDays(i))
				.collect(Collectors.toList());
	}

	/**
	 * Converts a Date to LocalDate (without time)
	 * 
	 * @param dateToConvert
	 * @return LocalDate
	 */
	public static LocalDate convertToLocalDate(Date dateToConvert) {
		return dateToConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}

	public static String getToday() {
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		return formatter.format(date);
	}

	/**
	 * 
	 * @param entity
	 * @return
	 */
	public static List<String> setDays(BookingDTO dto) {
		Date checkin = convertStringToDate(dto.getCheckin());
		Date checkout = convertStringToDate(dto.getCheckout());
		List<LocalDate> days = DateUtils.getDatesBetween(checkin, checkout);

		List<String> booked_days = new ArrayList<>();
		String dayString = "";
		for (LocalDate day : days) {
			dayString = day.toString();
			booked_days.add(dayString);
		}
		return booked_days;
	}

}
