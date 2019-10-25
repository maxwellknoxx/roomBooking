package com.challenge.roomBooking;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.challenge.roomBooking.entity.BookingEntity;
import com.challenge.roomBooking.repository.BookingRepository;
import com.challenge.roomBooking.service.impl.BookingServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class BookingUnitTests {
	
	@Mock
	private BookingRepository repository;
	
	@InjectMocks
	private BookingServiceImpl service;
	
	@Before
    public void setUp() throws Exception {
         MockitoAnnotations.initMocks(this);
    }
	

	 @Test
     public void shouldBook_returnBooking() {

         when(repository.save(any(BookingEntity.class))).thenReturn(new BookingEntity());

         BookingEntity bookingEntity = new BookingEntity();

         assertThat(service.book(bookingEntity), is(notNull()));
        
     }
	
	
	@Test
	void test() {
		fail("Not yet implemented");
	}

}
