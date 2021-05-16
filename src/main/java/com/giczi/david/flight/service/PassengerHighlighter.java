package com.giczi.david.flight.service;

import java.util.ArrayList;
import java.util.List;
import com.giczi.david.flight.domain.Passenger;
import com.giczi.david.flight.domain.PassengerDAO;

public class PassengerHighlighter {
	
	private List<PassengerDAO> highlightedPassengertStore;
	private String searchedExpression;
	private List<Integer> beginIndexStore;
	private List<Integer> endIndexStore;
	private final String preTag = "<span style=\"background-color: #FEE901;\">";
	private final String postTag = "</span>";


	public List<PassengerDAO> createInputPassengerStore(List<Passenger> inputPassengers, RoleService rolerService){
		
		highlightedPassengertStore = new ArrayList<>();
		
		for (Passenger passenger : inputPassengers) {
			PassengerDAO highlightedPassenger = new PassengerDAO();
			highlightedPassenger.setId(passenger.getId());
			highlightedPassenger.setFirstName(passenger.getFirstName());
			highlightedPassenger.setLastName(passenger.getLastName());
			highlightedPassenger.setDateOfBirth(passenger.getDateOfBirth());
			highlightedPassenger.setUsername(passenger.getUserName());
			highlightedPassenger.setPassword(passenger.getPassword());
			highlightedPassenger.setActivation(passenger.getActivation());
			highlightedPassenger.setEnabled(passenger.isEnabled());
			highlightedPassenger.setRole(rolerService.getPassengerRoleAsString(passenger.getRoles()));
			highlightedPassenger.setROLES(rolerService.getRoleStringStore());
			highlightedPassengertStore.add(highlightedPassenger);
		}
		
		return highlightedPassengertStore;
	}
		
	public List<PassengerDAO> getHighlightedPassengertStore() {
		return highlightedPassengertStore;
	}

	public void setSearchedExpression(String searchedExpression) {
		this.searchedExpression = searchedExpression.toLowerCase();
	}


	public void createHighlightedPassengerStore() {
		

		for (int i = 0; i < highlightedPassengertStore.size(); i++) {

			if (highlightedPassengertStore.get(i).getFirstName().toLowerCase().contains(searchedExpression)) {
				createBeginIndexStore(highlightedPassengertStore.get(i).getFirstName().toLowerCase());
				createEndIndexStore();
				highlightedPassengertStore.get(i).setFirstName(createHighlightedString(highlightedPassengertStore.get(i).getFirstName()));
			}
		    if(highlightedPassengertStore.get(i).getLastName().toLowerCase().contains(searchedExpression)) {
				createBeginIndexStore(highlightedPassengertStore.get(i).getLastName().toLowerCase());
				createEndIndexStore();
				highlightedPassengertStore.get(i).setLastName(createHighlightedString(highlightedPassengertStore.get(i).getLastName()));
			}
			if(highlightedPassengertStore.get(i).getDateOfBirth().contains(searchedExpression)) {
				createBeginIndexStore(highlightedPassengertStore.get(i).getDateOfBirth());
				createEndIndexStore();
				highlightedPassengertStore.get(i).setDateOfBirth(createHighlightedString(highlightedPassengertStore.get(i).getDateOfBirth()));
			}
			if(highlightedPassengertStore.get(i).getUsername().toLowerCase().contains(searchedExpression)) {
				createBeginIndexStore(highlightedPassengertStore.get(i).getUsername().toLowerCase());
				createEndIndexStore();
				highlightedPassengertStore.get(i).setUsername(createHighlightedString(highlightedPassengertStore.get(i).getUsername()));
			}
			if(highlightedPassengertStore.get(i).getPassword().toLowerCase().contains(searchedExpression)) {
				createBeginIndexStore(highlightedPassengertStore.get(i).getPassword().toLowerCase());
				createEndIndexStore();
				highlightedPassengertStore.get(i).setEncodedPassword(createHighlightedString(highlightedPassengertStore.get(i).getPassword()));
			}
			if(highlightedPassengertStore.get(i).getActivation().toLowerCase().contains(searchedExpression)) {
				createBeginIndexStore(highlightedPassengertStore.get(i).getActivation().toLowerCase());
				createEndIndexStore();
				highlightedPassengertStore.get(i).setActivation(createHighlightedString(highlightedPassengertStore.get(i).getActivation()));
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
