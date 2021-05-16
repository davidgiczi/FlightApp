package com.giczi.david.flight.domain;

import java.text.SimpleDateFormat;
import java.util.Date;
import com.giczi.david.flight.service.LangService;

public class FlightTicketDAO {

	
	private Long id;
	private String departureDate;
	private String departurePlace;
	private String arrivalDate;
	private String arrivalPlace;
	private String flightNumber;
	private String price;
	private boolean cancelled;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDepartureDate() {
		return departureDate;
	}
	public void setDepartureDate(Date departureDate) {
		switch (LangService.getLanguageByLocale()) {
		case 1:
			this.departureDate = new SimpleDateFormat("yyyy-MM-dd").format(departureDate);
			
			break;
		case 2:
			this.departureDate = new SimpleDateFormat("dd-MM-yyyy").format(departureDate);
			
			break;
		default:
			this.departureDate = new SimpleDateFormat("yyyy-MM-dd").format(departureDate);
			
		}
	}
	
	public void setDepartureDate(String departureDate) {
		this.departureDate = departureDate;
	}
	
	public String getDeparturePlace() {
		return departurePlace;
	}
	public void setDeparturePlace(String departurePlace) {
		this.departurePlace = departurePlace;
	}
	public String getArrivalDate() {
		return arrivalDate;
	}
	public void setArrivalDate(Date arrivalDate) {
		
		switch (LangService.getLanguageByLocale()) {
		case 1:
			this.arrivalDate = new SimpleDateFormat("yyyy-MM-dd").format(arrivalDate);
			
			break;
		case 2:
			this.arrivalDate = new SimpleDateFormat("dd-MM-yyyy").format(arrivalDate);
			
			break;
		default:
			this.arrivalDate = new SimpleDateFormat("yyyy-MM-dd").format(arrivalDate);
			
		}
		
	}
	
	public void setArrivalDate(String arrivalDate) {
		this.arrivalDate = arrivalDate;
	}
	
	public String getArrivalPlace() {
		return arrivalPlace;
	}
	public void setArrivalPlace(String arrivalPlace) {
		this.arrivalPlace = arrivalPlace;
	}
	public String getFlightNumber() {
		return flightNumber;
	}
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = String.valueOf(price);
	}
	
	public void setPriceAsString(String price) {
		this.price = price;
	}
	public boolean isCancelled() {
		return cancelled;
	}
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}
	
	
}
