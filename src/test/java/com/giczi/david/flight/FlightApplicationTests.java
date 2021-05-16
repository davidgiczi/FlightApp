package com.giczi.david.flight;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.giczi.david.flight.service.PassengerService;

@SpringBootTest
class FlightApplicationTests {

	private PassengerService service;
	
	@Autowired
	public void setService(PassengerService service) {
		this.service = service;
	}
	
	@Test
	void contextLoads() {
		assertNotNull(service);
	}
	
	
	
	
}
