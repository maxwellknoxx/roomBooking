package com.challenge.roomBooking.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.challenge.roomBooking.entity.BookingCalendar;
import com.challenge.roomBooking.model.BookingCalendarDTO;

@Component
public class BookingCalendarMapper {

	public static BookingCalendarDTO getDTO(BookingCalendar entity) {
		return BookingCalendarDTO.builder().id(entity.getId()).bookingId(entity.getBooking().getId())
				.bookedDays(DateUtils.getDatesBetween(DateUtils.convertStringToDate(entity.getCheckin()),
						DateUtils.convertStringToDate(entity.getCheckout())))
				.build();
	}

	public static List<BookingCalendarDTO> getListDTO(List<BookingCalendar> entities) {
		return entities.stream().filter(Objects::nonNull)
				.map(entity -> BookingCalendarDTO.builder().id(entity.getId()).bookingId(entity.getBooking().getId())
						.bookedDays(DateUtils.getDatesBetween(DateUtils.convertStringToDate(entity.getCheckin()),
								DateUtils.convertStringToDate(entity.getCheckout())))
						.build())
				.collect(Collectors.toList());
	}
	
	public static BookingCalendar parseDTOtoEntity(BookingCalendarDTO dto) {
		BookingCalendar bookingCalendar = new BookingCalendar();
		
		bookingCalendar.setCheckin(dto.getCheckin());
		bookingCalendar.setCheckout(dto.getCheckout());
		bookingCalendar.setDay(dto.getDay());
		
		return bookingCalendar;
	}
	
	public static List<BookingCalendar> parseListDTOtoListEntity(List<BookingCalendarDTO> listDTO){
		
		List<BookingCalendar> listBC = new ArrayList<>();
		BookingCalendar bookingCalendar;
		for(BookingCalendarDTO dto : listDTO) {
			bookingCalendar = new BookingCalendar();
			bookingCalendar = parseDTOtoEntity(dto);
			listBC.add(bookingCalendar);
		}
		return listBC;
	}
	
}
