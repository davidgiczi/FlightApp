package com.giczi.david.flight.service;

import java.util.ArrayList;
import java.util.List;
import com.giczi.david.flight.domain.FlightTicket;
import com.giczi.david.flight.domain.FlightTicketDAO;

public class FlightTicketHighlighter {

	private List<FlightTicketDAO> highlightedFlightTicketStore;
	private String searchedExpression;
	private List<Integer> beginIndexStore;
	private List<Integer> endIndexStore;
	private final String preTag = "<span style=\"background-color: #3996f3;\">";
	private final String postTag = "</span>";


	public List<FlightTicketDAO> createInputFlightTicketStore(List<FlightTicket> inputFlightTickets){
		
		highlightedFlightTicketStore = new ArrayList<>();
		
		for (FlightTicket flightTicket : inputFlightTickets) {
			FlightTicketDAO highlightedFlightTicket = new FlightTicketDAO();
			highlightedFlightTicket.setId(flightTicket.getId());
			highlightedFlightTicket.setDepartureDate(flightTicket.getDepartureDate());
			highlightedFlightTicket.setDeparturePlace(flightTicket.getDeparturePlace());
			highlightedFlightTicket.setArrivalDate(flightTicket.getArrivalDate());
			highlightedFlightTicket.setArrivalPlace(flightTicket.getArrivalPlace());
			highlightedFlightTicket.setFlightNumber(flightTicket.getFlightNumber());
			highlightedFlightTicket.setPrice(flightTicket.getPrice());
			highlightedFlightTicket.setCancelled(flightTicket.isDeleted());
			highlightedFlightTicketStore.add(highlightedFlightTicket);
		}
		
		return highlightedFlightTicketStore;
	}
	
	
	public List<FlightTicketDAO> getHighlightedFlightTicketStore() {
		return highlightedFlightTicketStore;
	}
	
	public void setSearchedExpression(String searchedExpression) {
		this.searchedExpression = searchedExpression.toLowerCase();
	}


	public void createHighlightedFlightTicketStore() {

		for (int i = 0; i < highlightedFlightTicketStore.size(); i++) {

			if (highlightedFlightTicketStore.get(i).getDepartureDate().contains(searchedExpression)) {
				createBeginIndexStore(highlightedFlightTicketStore.get(i).getDepartureDate());
				createEndIndexStore();
				highlightedFlightTicketStore.get(i).setDepartureDate(createHighlightedString(highlightedFlightTicketStore.get(i).getDepartureDate()));
			}
		    if(highlightedFlightTicketStore.get(i).getDeparturePlace().toLowerCase().contains(searchedExpression)) {
				createBeginIndexStore(highlightedFlightTicketStore.get(i).getDeparturePlace().toLowerCase());
				createEndIndexStore();
				highlightedFlightTicketStore.get(i).setDeparturePlace(createHighlightedString(highlightedFlightTicketStore.get(i).getDeparturePlace()));
			}
			if(highlightedFlightTicketStore.get(i).getArrivalDate().contains(searchedExpression)) {
				createBeginIndexStore(highlightedFlightTicketStore.get(i).getArrivalDate());
				createEndIndexStore();
				highlightedFlightTicketStore.get(i).setArrivalDate(createHighlightedString(highlightedFlightTicketStore.get(i).getArrivalDate()));
			}
			if(highlightedFlightTicketStore.get(i).getArrivalPlace().toLowerCase().contains(searchedExpression)) {
				createBeginIndexStore(highlightedFlightTicketStore.get(i).getArrivalPlace().toLowerCase());
				createEndIndexStore();
				highlightedFlightTicketStore.get(i).setArrivalPlace(createHighlightedString(highlightedFlightTicketStore.get(i).getArrivalPlace()));
			}
			if(highlightedFlightTicketStore.get(i).getFlightNumber().toLowerCase().contains(searchedExpression)) {
				createBeginIndexStore(highlightedFlightTicketStore.get(i).getFlightNumber().toLowerCase());
				createEndIndexStore();
				highlightedFlightTicketStore.get(i).setFlightNumber(createHighlightedString(highlightedFlightTicketStore.get(i).getFlightNumber()));
			}
			if(highlightedFlightTicketStore.get(i).getPrice().contains(searchedExpression)) {
				createBeginIndexStore(highlightedFlightTicketStore.get(i).getPrice());
				createEndIndexStore();
				highlightedFlightTicketStore.get(i).setPriceAsString(createHighlightedString(highlightedFlightTicketStore.get(i).getPrice()));
			}
		}

		
	}

	
	private void createBeginIndexStore(String containerText) {

		beginIndexStore = new ArrayList<>();

		for (int i = 0; i <= containerText.length() - searchedExpression.length(); i++) {

			if (containerText.charAt(i) == searchedExpression.charAt(0)
					&& containerText.substring(i, i + searchedExpression.length()).equals(searchedExpression)) {

				beginIndexStore.add(i);

			}

		}

	}

	private void createEndIndexStore() {
		
		endIndexStore = new ArrayList<>();
		
		for(int i = 0; i < beginIndexStore.size(); i++) {
			
		int endIndex = beginIndexStore.get(i) + searchedExpression.length() - 1;
			
		if(i + 1 < beginIndexStore.size() && endIndex >= beginIndexStore.get(i + 1)) {
			
			continue;
		
		}
			
		endIndexStore.add(endIndex);
		
		}
			
	}

	
	
	private String createHighlightedString(String text) {

		char[] container = text.toCharArray();
		StringBuilder builder = new StringBuilder();
		boolean isOpenTag = false;

		for (int i = 0; i < container.length; i++) {

			
		 if (beginIndexStore.contains(i) && !isOpenTag) {

				builder.append(preTag);
				isOpenTag = true;

			} 
			 	 
		 builder.append(container[i]);	 
		 
		 if (endIndexStore.contains(i) && isOpenTag) {

				builder.append(postTag);
				isOpenTag = false;

			} 
					
	}


		return builder.toString();
	}


}
