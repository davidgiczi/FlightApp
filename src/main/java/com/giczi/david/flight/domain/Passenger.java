package com.giczi.david.flight.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.springframework.format.annotation.DateTimeFormat;


@Entity
@Table(name = "passengers")
public class Passenger {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String firstName;
	private String lastName;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dateOfBirth;
	@OneToMany(mappedBy = "passenger")
	private List<FlightTicket> flightTickets;
	@Column(unique = true, nullable = false)
	private String userName;
	@Column(nullable = false)
	private String password;
	private boolean enabled;
	private String activation;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(
			name= "passengers_roles",
			joinColumns= {@JoinColumn(name = "passenger_id")},
			inverseJoinColumns = {@JoinColumn(name = "role_id")}
			)
	private Set<Role> roles = new HashSet<>();
	
	public Passenger() {
	}

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

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public List<FlightTicket> getFlightTickets() {
		return flightTickets;
	}

	public void setFlightTickets(List<FlightTicket> flightTicets) {
		this.flightTickets = flightTicets;
	}

	public String getUserName() {
		return userName; 
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public void addRoles(String roleName) {
		
		if(this.roles == null || this.roles.isEmpty()) {
			this.roles = new HashSet<>();
		}
			this.roles.add(new Role(roleName));
		
	}

	public Boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public String getActivation() {
		return activation;
	}

	public void setActivation(String activation) {
		this.activation = activation;
	}

	@Override
	public String toString() {
		return "Passenger [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", dateOfBirth="
				+ dateOfBirth + ", userName=" + userName + ", password=" + password + ", enabled=" + enabled
				+ ", activation=" + activation + ", roles=" + roles + "]";
	}
	
	
}
