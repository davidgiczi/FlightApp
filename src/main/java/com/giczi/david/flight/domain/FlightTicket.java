package com.giczi.david.flight.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "tickets")
public class FlightTicket {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String flightNumber;
	private String departurePlace;
	private String arrivalPlace;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date departureDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date arrivalDate;
	@ManyToOne
	private Passenger passenger;
	private int price;
	private boolean deleted;
		
	public FlightTicket() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDeparturePlace() {
		return departurePlace;
	}

	public void setDeparturePlace(String departurePlace) {
		this.departurePlace = departurePlace;
	}

	public String getArrivalPlace() {
		return arrivalPlace;
	}

	public void setArrivalPlace(String arrivalPlace) {
		this.arrivalPlace = arrivalPlace;
	}

	public Date getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(Date arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	public Date getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(Date departureDate) {
		this.departureDate = departureDate;
	}

	public Passenger getPassenger() {
		return passenger;
	}

	public void setPassenger(Passenger passenger) {
		this.passenger = passenger;
	}
	

	public String getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	@Override
	public String toString() {
		return "FlightTicket [id=" + id + ", flightNumber=" + flightNumber + ", departurePlace=" + departurePlace
				+ ", arrivalPlace=" + arrivalPlace + ", departureDate=" + departureDate + ", arrivalDate=" + arrivalDate
				+  ", price=" + price + ", deleted=" + deleted + "]";
	}

	
}
