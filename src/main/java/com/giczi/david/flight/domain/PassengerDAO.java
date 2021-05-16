package com.giczi.david.flight.domain;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import com.giczi.david.flight.service.EncoderService;
import com.giczi.david.flight.service.LangService;


public class PassengerDAO  {

	private Long id;
	private String firstName;
	private String lastName;
	private String dateOfBirth;
	private String username;
	private String password;
	private String activation;
	private String role;
	private boolean enabled;
	private List<String> ROLES;
	

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		
		switch (LangService.getLanguageByLocale()) {
		case 1:
			this.dateOfBirth = new SimpleDateFormat("yyyy-MM-dd").format(dateOfBirth);
			break;
		case 2:
			this.dateOfBirth = new SimpleDateFormat("dd-MM-yyyy").format(dateOfBirth);
			break;
		default:
			this.dateOfBirth = new SimpleDateFormat("yyyy-MM-dd").format(dateOfBirth);
		}
		
	}
	
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = EncoderService.decodeByBase64(password);
	}
	
	public void setEncodedPassword(String encodedPassword) {
		this.password = encodedPassword;
	}
	
	public String getActivation() {
		return activation;
	}
	public void setActivation(String activation) {
		
		if(activation == null) {
		this.activation = "-";	
		}
		else {
		this.activation = activation;		
		}
	}
	
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public List<String> getROLES() {
		return ROLES;
	}
	public void setROLES(List<String> rOLES) {
		ROLES = rOLES;
	}
	
}
