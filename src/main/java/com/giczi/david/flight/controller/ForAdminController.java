package com.giczi.david.flight.controller;


import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.giczi.david.flight.domain.FlightTicket;
import com.giczi.david.flight.domain.FlightTicketDAO;
import com.giczi.david.flight.domain.Passenger;
import com.giczi.david.flight.domain.PassengerDAO;
import com.giczi.david.flight.domain.Role;
import com.giczi.david.flight.repository.RoleRepository;
import com.giczi.david.flight.service.FlightTicketService;
import com.giczi.david.flight.service.PassengerService;

@Controller
@RequestMapping("/admin")
public class ForAdminController {

	
	private PassengerService passengerService;
	private FlightTicketService ticketService;
	private RoleRepository roleRepo;
 	
	@Autowired
	public void setRoleRepo(RoleRepository roleRepo) {
		this.roleRepo = roleRepo;
	}


	@Autowired
	public void setPassengerService(PassengerService passengerService) {
		this.passengerService = passengerService;
	}
	

	@Autowired
	public void setTicketService(FlightTicketService ticketService) {
		this.ticketService = ticketService;
	}




	@RequestMapping("/clients")
	public String goClientsPage(Model model) {
		
		model.addAttribute("clients", passengerService.findAll());
		
		return "clients";
	}
	
	@RequestMapping("enter")
	public String enterUserAccount(@RequestParam("id") Long id, HttpServletRequest request, Model model) {
		
		Optional<Passenger> passenger = passengerService.findPassengerById(id); 
		
		UsernamePasswordAuthenticationToken token = 
	            new UsernamePasswordAuthenticationToken(passenger.get().getUserName(), passenger.get().getPassword());
	    token.setDetails(new WebAuthenticationDetails(request));
	    AuthenticationProvider authenticationProvider = new AuthenticationProvider() {
			
			@Override
			public boolean supports(Class<?> authentication) {
				
				return authentication.equals(UsernamePasswordAuthenticationToken.class);
			}
			
			@Override
			public Authentication authenticate(Authentication authentication) throws AuthenticationException {
				
				String username = authentication.getName();
		        String password = authentication.getCredentials()
		            .toString();
		        
		       Role guestRole = roleRepo.findByRole("ROLE_GUEST");
			    
		 	   if(guestRole != null) {
		 		   passenger.get().getRoles().add(guestRole);
		 	   }
		 	   else {
		 		   passenger.get().addRoles("ROLE_GUEST");
		 	   }
		            
				return new UsernamePasswordAuthenticationToken
			              (username, password, Collections.emptyList());
			}
		};
		
		Authentication auth = authenticationProvider.authenticate(token);
		
	    SecurityContextHolder.getContext().setAuthentication(auth);	
	    
	    
	    
		passengerService.save(passenger.get());
				
		return "redirect:/flight/order";
	}
	
	@RequestMapping("enabled")
	public String enabledUserAccount(@RequestParam("id") Long id) {
		
		Optional<Passenger> passenger = passengerService.findPassengerById(id);
		
		if(passenger.isPresent()) {
			if(passenger.get().isEnabled()) {
				passenger.get().setEnabled(false);
			}
			else {
				passenger.get().setEnabled(true);
			}
			
			passengerService.save(passenger.get());
			
		}
		return "redirect:/admin/clients";
	}
	
	@RequestMapping("role")
	public String setRole(@RequestParam("id") Long id, @RequestParam("role") String roleName) {
		
		Optional<Passenger> passenger = passengerService.findPassengerById(id);
		
		if(passenger.isPresent()) {
		
			Role role = roleRepo.findByRole(roleName);
			
			if(role != null) {
				passenger.get().getRoles().clear();
				passenger.get().getRoles().add(role);
			}
			else {
				passenger.get().addRoles(roleName);
			}
			passengerService.save(passenger.get());
		}
		
		return "redirect:/admin/clients";
	}
	
	@RequestMapping("passenger/delete")
	public String deleteUserAccount(@RequestParam("id") Long id) {
		
		Optional<Passenger> passenger = passengerService.findPassengerById(id);
		
		if(passenger.isPresent()) {
		passenger.get().setRoles(null);
		ticketService.deleteAllPassengerTickets(id);
		passengerService.delete(passenger.get());
		}
		
		return "redirect:/admin/clients";
	}
	
	@RequestMapping("/passenger/search")
	public String searchPassenger(@RequestParam(value = "text") String text, @RequestParam("lang") String lang, Model model) {
		
		
		if(text.isEmpty()) {
			return "redirect:/admin/clients";
		}else {
			List<PassengerDAO> clients = passengerService.findByText(text);
			model.addAttribute("txt", text);
			model.addAttribute("clients", clients);	
			
		}
		
		return "clients";
	}
		
	
	@RequestMapping("/reservations")
	public String goClientReservations(@RequestParam("id") Long id, @RequestParam("lang") String lang, Model model) {
		
		
		Optional<Passenger> passenger = passengerService.findPassengerById(id);
		
		if(passenger.isPresent()) {
			PassengerDAO pass = new PassengerDAO();
			pass.setId(passenger.get().getId());
			pass.setFirstName(passenger.get().getFirstName());
			pass.setLastName(passenger.get().getLastName());
			pass.setDateOfBirth(passenger.get().getDateOfBirth());
			pass.setUsername(passenger.get().getUserName());
			pass.setPassword(passenger.get().getPassword());
			model.addAttribute("client", pass);
			List<FlightTicketDAO> tickets = ticketService.findTicketsByPassenger(passenger.get());
			model.addAttribute("orderedTickets", tickets);
			model.addAttribute("passengerId", pass.getId());
		}
		
		return "client_reservations";
	}
	
	@RequestMapping("activate")
	public String activateTicket(@RequestParam("id") Long id, @RequestParam("lang") String lang, @RequestParam("act") String activate, Model model) {
		
		Optional<FlightTicket> ticket = ticketService.findById(id);
		Long ordererId = ticket.get().getPassenger().getId();
		
		if(ticket.isPresent()) {
			
			if("yes".equals(activate) && ticket.get().isDeleted()) {
				ticket.get().setDeleted(false);
			}
			else if("yes".equals(activate) && !ticket.get().isDeleted()) {
				ticket.get().setDeleted(true);
			}
			ticketService.saveFlightTicket(ticket.get());
		}
		
		return  "redirect:/admin/reservations?id=" + ordererId + "&lang=" + LocaleContextHolder.getLocale();
	}
	
	@RequestMapping("ticket/delete")
	public String deleteTicket(@RequestParam("id") Long id, @RequestParam("lang") String lang, Model model) {
		
		Optional<FlightTicket> ticket = ticketService.findById(id);
		Long ordererId = ticket.get().getPassenger().getId();
		
		if(ticket.isPresent()) {
			ticketService.deleteFlightTicket(ticket.get());
		}
		
		return "redirect:/admin/reservations?id=" + ordererId + "&lang=" + LocaleContextHolder.getLocale();
	}
	
	@RequestMapping("/ticket/search")
	public String searchTicketByAdmin(@RequestParam(value = "text") String text, @RequestParam(value="lang") String lang, @RequestParam(value="id") String id,  Model model) {
		
		Long passenger_id = Long.valueOf(id);
		Optional<Passenger> passenger = passengerService.findPassengerById(passenger_id);
		List<FlightTicketDAO> tickets;
	
		if(text.isEmpty()) {
		tickets = ticketService.findTicketsByPassenger(passenger.get());
	}
		else {
		tickets = ticketService.findByTextAndPassengerId(text, passenger_id, true);
	}
		
		PassengerDAO pass = new PassengerDAO();
		pass.setId(passenger.get().getId());
		pass.setFirstName(passenger.get().getFirstName());
		pass.setLastName(passenger.get().getLastName());
		pass.setDateOfBirth(passenger.get().getDateOfBirth());
		pass.setUsername(passenger.get().getUserName());
		pass.setPassword(passenger.get().getPassword());
		model.addAttribute("client", pass);
		model.addAttribute("passengerId", pass.getId());
		model.addAttribute("orderedTickets", tickets);
		model.addAttribute("txt", text);
			
		return "client_reservations";
	}
	
	@RequestMapping("/ticket/getModifyTicket")
	public String modifyTicket(@RequestParam(value="id") String id,  @RequestParam(value="lang") String lang, Model model) {
	
		Long ticketId = Long.valueOf(id);
		Optional<FlightTicket> ticket = ticketService.findById(ticketId);
		Passenger orderer = ticket.get().getPassenger();
		
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Budapest"));
		
		ticketService.init();
		model.addAttribute("lang", LocaleContextHolder.getLocale().toString());
		model.addAttribute("departurePlaces", ticketService.getDeparturePlaces());
		model.addAttribute("departurePlace", ticket.get().getDeparturePlace());
		calendar.setTime(ticket.get().getDepartureDate());
		model.addAttribute("dep_year",  calendar.get(Calendar.YEAR));
		model.addAttribute("dep_month",  calendar.get(Calendar.MONTH) + 1);
		model.addAttribute("dep_day",  calendar.get(Calendar.DAY_OF_MONTH));
		model.addAttribute("arrivalPlaces", ticketService.getArrivalPlaces());
		model.addAttribute("arrivalPlace", ticket.get().getArrivalPlace());
		model.addAttribute("flightNumbers", ticketService.getPlanes());
		model.addAttribute("flightNumber", ticket.get().getFlightNumber());
		model.addAttribute("price", ticket.get().getPrice());
		model.addAttribute("passengerId", orderer.getId());
		model.addAttribute("ticket", new FlightTicket());
		model.addAttribute("ticketId", ticketId);
			
		return "modify_ticket";
	}
	
	@RequestMapping("/ticket/setModifiedTicket")
	public String modifyTicket(@ModelAttribute FlightTicket ticket, HttpServletRequest request) {
		
		Long passId = Long.valueOf(request.getParameter("passengerId"));
		Long ticketId = Long.valueOf(request.getParameter("ticketId"));
		
		Optional<FlightTicket> modifiedTicket = ticketService.findById(ticketId);
		
		if(modifiedTicket.isPresent()) {
			modifiedTicket.get().setDepartureDate(ticket.getDepartureDate());
			modifiedTicket.get().setDeparturePlace(ticket.getDeparturePlace());
			modifiedTicket.get().setArrivalDate(ticket.getArrivalDate());
			modifiedTicket.get().setArrivalPlace(ticket.getArrivalPlace());
			modifiedTicket.get().setFlightNumber(ticket.getFlightNumber());
			modifiedTicket.get().setPrice(ticket.getPrice());
			ticketService.saveFlightTicket(modifiedTicket.get());
		}
		
		return "redirect:/admin/reservations?id=" + passId + "&lang=" + LocaleContextHolder.getLocale();
}

	
	
	
}	
	
	