package com.giczi.david.flight.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.giczi.david.flight.domain.FlightTicket;
import com.giczi.david.flight.domain.FlightTicketDAO;
import com.giczi.david.flight.domain.Passenger;
import com.giczi.david.flight.service.EncoderService;
import com.giczi.david.flight.service.FlightTicketService;
import com.giczi.david.flight.service.PassengerService;
import com.giczi.david.flight.service.RoleService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/flight")
public class ForUserController {

	private PassengerService passengerService;
	private FlightTicketService ticketService;
	private RoleService roleService;
	
	@Autowired	
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	@Autowired
	public ForUserController(PassengerService passengerService, FlightTicketService ticketService) {
		this.passengerService = passengerService;
		this.ticketService = ticketService;
	}
	
	@RequestMapping("/reservations")
	public String showAllTickets(Model model) {
		
		Passenger passenger = passengerService.findPassengerByUserName(getAuthUser());
		List<FlightTicketDAO> tickets = ticketService.findNotDeletedTicketsByPassenger(passenger);
		
		model.addAttribute("orderedTickets", tickets);
			
		return "reservations";
	}
	
	@RequestMapping("/order")
	public String goOrderPage(Model model) {
		
		
		if("ROLE_ADMIN".equals(roleService.getPassengerRoleAsString(passengerService.findPassengerByUserName(getAuthUser()).getRoles()))){
			
			return "redirect:/admin/clients";
		}
		
		ticketService.init(); //for demo only
		model.addAttribute("departurePlaces", ticketService.getDeparturePlaces());
		model.addAttribute("arrivalPlaces", ticketService.getArrivalPlaces());
		model.addAttribute("planes", ticketService.getPlanes());
		model.addAttribute("cost", (int)(Math.random() * 20 + 1) * 500 + 10000);
		model.addAttribute("date", new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis())));
		model.addAttribute("ticket", new FlightTicket());
		 
		return "order";
	}
	
	@RequestMapping("/reserve")
	public String goOrderPage(@ModelAttribute FlightTicket ticket) {

		Passenger passenger = passengerService.findPassengerByUserName(getAuthUser());
		ticket.setPassenger(passenger);
		ticketService.saveFlightTicket(ticket);
		
		return "redirect:/flight/reservations";
	}
	
	@RequestMapping("/cancel")
	public String cancelReservation(@RequestParam(value = "id") Long id) {
		
		ticketService.cancelTicket(id);
		
		return "redirect:/flight/reservations";
	}
	
	
	
	@RequestMapping("/ticket/search")
	public String searchTicketByPassenger(@RequestParam(value = "text") String text, @RequestParam(value="lang") String lang, Model model) {
		
		if(text.isEmpty()) {
		return "redirect:/flight/reservations";
		}
		else{
			Passenger passenger =  passengerService.findPassengerByUserName(getAuthUser());
			List<FlightTicketDAO> tickets = ticketService.findByTextAndPassengerId(text, passenger.getId(), false);
			model.addAttribute("txt", text);
			model.addAttribute("orderedTickets", tickets);
		}
		
		return "reservations";
	}
	
	@RequestMapping("/registration")
	public String goRegistrationPage(Model model) {
		
		model.addAttribute("user", new Passenger());
	
		return "regist";
	}
	
	@RequestMapping(value = "/activation/{code}", method = RequestMethod.GET)
	public String activation(@PathVariable("code") String code, Model model) {
		
		if(passengerService.userActivation(code)) {
			model.addAttribute("activationSuccess", true);
		}
		else {
			model.addAttribute("activationSuccess", false);
		}
		
		return "auth/login";
	}
	
	@PostMapping("/reg")
	public String registration(@ModelAttribute Passenger user, RedirectAttributes attribute, Model model) {
		
		if(!passengerService.registerPassenger(user)) {
			
			attribute.addAttribute("exists", true);
			
			return "redirect:/flight/registration";
		}
		
		model.addAttribute("needfulActivation", true);
		
		return "auth/login";
	}
	
	@PostMapping("/changePassword")
	public String changePassword(HttpServletRequest request, RedirectAttributes attr) {
		
		Passenger passenger = passengerService.findPassengerByUserName(getAuthUser());
		passenger.setPassword(EncoderService.encodeByBase64(request.getParameter("pass")));
		passengerService.save(passenger);
		attr.addAttribute("changedPass", true);
		
		return "redirect:/flight/order";
	}
	
	private String getAuthUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication.getName();
	}
	
}
