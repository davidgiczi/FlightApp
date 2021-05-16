package com.giczi.david.flight.config;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import com.giczi.david.flight.domain.Passenger;
import com.giczi.david.flight.domain.Role;
import com.giczi.david.flight.service.PassengerService;


public class CustomLogoutHandler implements LogoutHandler {
	
	private PassengerService passengerService;
	
	public CustomLogoutHandler(PassengerService passengerService) {
	
		this.passengerService = passengerService;
	}


	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		
		Passenger authedPassenger = passengerService.findPassengerByUserName(authentication.getName());
		Role guest = new Role("ROLE_GUEST");
		if(authedPassenger != null && authedPassenger.getRoles().contains(guest)) {
		authedPassenger.getRoles().remove(guest);
		passengerService.save(authedPassenger);
		}
	}

	
	

}
