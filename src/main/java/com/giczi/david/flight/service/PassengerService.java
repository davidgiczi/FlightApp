package com.giczi.david.flight.service;

import java.util.List;
import java.util.Optional;

import com.giczi.david.flight.domain.Passenger;
import com.giczi.david.flight.domain.PassengerDAO;

public interface PassengerService {

	boolean registerPassenger(Passenger passengerToRegister);
	boolean userActivation(String code);
	Passenger findPassengerByUserName(String username);
	List<PassengerDAO> findAll();
	Optional<Passenger> findPassengerById(Long id);
	void save(Passenger passenger);
	void delete(Passenger passenger);
	List<PassengerDAO> findByText(String text);
}
