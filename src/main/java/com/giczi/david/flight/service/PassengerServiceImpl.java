package com.giczi.david.flight.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.giczi.david.flight.domain.Passenger;
import com.giczi.david.flight.domain.PassengerDAO;
import com.giczi.david.flight.domain.Role;
import com.giczi.david.flight.repository.PassengerRepository;

@Service
public class PassengerServiceImpl implements PassengerService, UserDetailsService {
	
	private PassengerRepository passengerRepo;
	private RoleService roleService;
	private EmailService emailService;
	
	@Autowired
	public void setPassengerRepo(PassengerRepository passengerRepo) {
		this.passengerRepo = passengerRepo;
	}
	
	@Autowired
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	@Autowired
	public void setEmailService(EmailService emailService) {
		this.emailService = emailService;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Passenger passenger = passengerRepo.findByUserName(username);
		
		if(passenger == null) {
			throw new UsernameNotFoundException(username);
		}	
		
		return new PassengerDetailsImpl(passenger);
	}

	@Override
	public boolean registerPassenger(Passenger passengerToRegister) {
		
		Passenger passengerCheck = passengerRepo.findByUserName(passengerToRegister.getUserName());
		
		if(passengerCheck != null) {
			return false;
		}
		
		Role userRole = roleService.findByRole("ROLE_USER");
		
		if(userRole != null) {
			passengerToRegister.getRoles().add(userRole);
		}
		else {
			passengerToRegister.addRoles("ROLE_USER");
		}
		
		passengerToRegister.setEnabled(false);
		String activationKey = generatedKey();
		passengerToRegister.setActivation(activationKey);
		//emailService.sendMeassage(passengerToRegister.getUserName(), passengerToRegister.getFirstName(), passengerToRegister.getLastName(), activationKey);
		//passengerToRegister.setPassword(EncoderService.getBCryptEncoder().encode(passengerToRegister.getPassword()));
		passengerToRegister.setPassword(EncoderService.encodeByBase64(passengerToRegister.getPassword()));
		passengerRepo.save(passengerToRegister);
		
		return true;
	}

	
	private String generatedKey() {
		
		Random random = new Random();
		char [] word = new char[16];
		for(int j = 0; j < word.length; j++) {
			word[j] = (char) ('a' + random.nextInt(26));
		}
		
		return new String(word);
	}

	@Override
	public boolean userActivation(String code) {
		
		Passenger passenger = passengerRepo.findByActivation(code);
		
		if(passenger == null) {
			return false;
		}
		
		passenger.setActivation(null);
		passenger.setEnabled(true);
		passengerRepo.save(passenger);
		
		return true;
	}


	@Override
	public Passenger findPassengerByUserName(String username) {
		return passengerRepo.findByUserName(username);
	}

	@Override
	public List<PassengerDAO> findAll() {
		
		List<Passenger> clients = passengerRepo.findAll();
		List<PassengerDAO> passengerDAOStore = new ArrayList<>();
		
		for (Passenger passenger : clients) {
			PassengerDAO passengerDAO = new PassengerDAO();
			passengerDAO.setId(passenger.getId());
			passengerDAO.setFirstName(passenger.getFirstName());
			passengerDAO.setLastName(passenger.getLastName());
			passengerDAO.setDateOfBirth(passenger.getDateOfBirth());
			passengerDAO.setUsername(passenger.getUserName());
			passengerDAO.setPassword(passenger.getPassword());
			passengerDAO.setActivation(passenger.getActivation());
			passengerDAO.setRole(roleService.getPassengerRoleAsString(passenger.getRoles()));
			passengerDAO.setEnabled(passenger.isEnabled());
			passengerDAO.setROLES(roleService.getRoleStringStore());
			passengerDAOStore.add(passengerDAO);
		}
		
		
		return passengerDAOStore;
	};

	@Override
	public Optional<Passenger> findPassengerById(Long id) {
		
		return passengerRepo.findById(id);
	}

	@Override
	public void save(Passenger passenger) {
		
		passengerRepo.save(passenger);
		
	}

	@Override
	public void delete(Passenger passenger) {
		passengerRepo.delete(passenger);
	}

	@Override
	public List<PassengerDAO> findByText(String text) {
		
		List<Passenger> passengers= new ArrayList<>();
		
		if(Character.isLetter(text.charAt(0)) && Character.isUpperCase(text.charAt(0))) {
			text = text.charAt(0) + text.substring(1, text.length()).toLowerCase();
		}
		else if(Character.isLetter(text.charAt(0)) && Character.isLowerCase(text.charAt(0))) {
			text = String.valueOf(text.charAt(0)).toUpperCase() + text.substring(1, text.length()).toLowerCase();
		}
		
		passengers = passengerRepo.findByText(text);
		
		if(passengers.isEmpty()) {
		passengers= passengerRepo.findByText(text.toUpperCase());
		}
		if(passengers.isEmpty()) {
		passengers.addAll(passengerRepo.findByText(text.toLowerCase()));
		}
		
		passengers.addAll(passengerRepo.findByPassword(EncoderService.encodeByBase64(text)));
		
		PassengerHighlighter highlighter = new PassengerHighlighter();
		highlighter.setSearchedExpression(text);
		highlighter.createInputPassengerStore(passengers, roleService);
		highlighter.createHighlightedPassengerStore();
		
		return highlighter.getHighlightedPassengertStore();
	
	}
	
	
	
}
