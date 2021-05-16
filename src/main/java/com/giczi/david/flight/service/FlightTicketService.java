package com.giczi.david.flight.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.giczi.david.flight.domain.FlightTicket;
import com.giczi.david.flight.domain.FlightTicketDAO;
import com.giczi.david.flight.domain.Passenger;
import com.giczi.david.flight.repository.FlightTicketRepository;

@Service
public class FlightTicketService {

	
	private FlightTicketRepository ticketRepo;
	private List<String> departurePlaces;
	private List<String> arrivalPlaces;
	private List<String> planes;
	
	@Autowired
	public void setTicketRepo(FlightTicketRepository ticketRepo) {
		this.ticketRepo = ticketRepo;
	}
	
	public void init() {
		
		departurePlaces = new ArrayList<>();
		departurePlaces.add("Budapest");
		departurePlaces.add("Debrecen");
		arrivalPlaces = new ArrayList<>();
		arrivalPlaces.add("Prague");
		arrivalPlaces.add("Berlin");
		arrivalPlaces.add("Barcelona");
		arrivalPlaces.add("London");
		arrivalPlaces.add("Paris");
		arrivalPlaces.add("Helsinki");
		arrivalPlaces.add("Moscow");
		planes = new ArrayList<>();
		planes.add("NKS-137");
		planes.add("THY-1G6");
		planes.add("N-X-211");
	}
	
		
	public List<String> getDeparturePlaces() {
		return departurePlaces;
	}


	public List<String> getArrivalPlaces() {
		return arrivalPlaces;
	}


	public List<String> getPlanes() {
		return planes;
	}


	public void saveFlightTicket(FlightTicket ticket) {
		
		if(ticket != null) {
			ticketRepo.save(ticket);
		}
		
	}

	public List<FlightTicketDAO> findNotDeletedTicketsByPassenger(Passenger passenger){
		
		List<FlightTicket> tickets = ticketRepo.findNotDeletedTicketsByPassengerId(passenger.getId());
		
		return new FlightTicketHighlighter().createInputFlightTicketStore(tickets);
	}
	
	public void cancelTicket(Long id) {
		
		Optional<FlightTicket> ticketOtional = ticketRepo.findById(id);
		FlightTicket ticket = ticketOtional.get();
		ticket.setDeleted(true);
		ticketRepo.save(ticket);
		
	}
	public List<FlightTicketDAO> findByTextAndPassengerId(String text, Long id, boolean all){
		
		List<FlightTicket> tickets= new ArrayList<>();
		
		if(Character.isLetter(text.charAt(0)) && Character.isUpperCase(text.charAt(0))) {
			text = text.charAt(0) + text.substring(1, text.length()).toLowerCase();
		}
		else if(Character.isLetter(text.charAt(0)) && Character.isLowerCase(text.charAt(0))) {
			text = String.valueOf(text.charAt(0)).toUpperCase() + text.substring(1, text.length()).toLowerCase();
		}
		
		if(all) {
			tickets = ticketRepo.findAllTicketsByTextAndPassengerId(text, id);
		}
		else {
			tickets = ticketRepo.findNotCancelledTicketsByTextAndPassengerId(text, id);
		}
		
		if(tickets.isEmpty() && all) {
			tickets = ticketRepo.findAllTicketsByTextAndPassengerId(text.toUpperCase(), id);
		}
		else if(tickets.isEmpty() && !all) {
			tickets = ticketRepo.findNotCancelledTicketsByTextAndPassengerId(text.toUpperCase(), id);
		}
		
		
		if(tickets.isEmpty() && all) {
			tickets = ticketRepo.findAllTicketsByTextAndPassengerId(text.toLowerCase(), id);
		}
		else if(tickets.isEmpty() && !all) {
			tickets = ticketRepo.findNotCancelledTicketsByTextAndPassengerId(text.toLowerCase(), id);
		}
		
		FlightTicketHighlighter highlighter = new FlightTicketHighlighter();
		highlighter.setSearchedExpression(text);
		highlighter.createInputFlightTicketStore(tickets);
		highlighter.createHighlightedFlightTicketStore();
		
		return highlighter.getHighlightedFlightTicketStore();
	}
	
	public void deleteAllPassengerTickets(Long id) {
		List<FlightTicket> passengerTickets = ticketRepo.findByPassengerId(id);
		for (FlightTicket flightTicket : passengerTickets) {
			ticketRepo.delete(flightTicket);
		}
	}
	
	public List<FlightTicketDAO> findTicketsByPassenger(Passenger passenger){
		
		List<FlightTicket> tickets = ticketRepo.findByPassengerId(passenger.getId());
		List<FlightTicketDAO> ticketDAOStore = new ArrayList<>();
		
		for (FlightTicket ticket : tickets) {
			FlightTicketDAO ticketDAO = new FlightTicketDAO();
			ticketDAO.setId(ticket.getId());
			ticketDAO.setDepartureDate(ticket.getDepartureDate());
			ticketDAO.setDeparturePlace(ticket.getDeparturePlace());
			ticketDAO.setArrivalDate(ticket.getArrivalDate());
			ticketDAO.setArrivalPlace(ticket.getArrivalPlace());
			ticketDAO.setFlightNumber(ticket.getFlightNumber());
			ticketDAO.setPrice(ticket.getPrice());
			ticketDAO.setCancelled(ticket.isDeleted());
			ticketDAOStore.add(ticketDAO);
		}
	
		return  ticketDAOStore;
	}
	
	public Optional<FlightTicket> findById(Long id) {
		
		return ticketRepo.findById(id);
	}
	
	public void deleteFlightTicket(FlightTicket ticket) {
		ticketRepo.delete(ticket);
	}
}
