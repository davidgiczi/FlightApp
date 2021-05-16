package com.giczi.david.flight.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.giczi.david.flight.domain.Passenger;
import com.giczi.david.flight.domain.Role;

public class PassengerDetailsImpl implements UserDetails {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Passenger passenger;
		
	public PassengerDetailsImpl(Passenger passenger) {
	
		this.passenger = passenger;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		Collection<GrantedAuthority> authorities = new HashSet<>();
		Set<Role> roles = passenger.getRoles();
		for (Role role : roles) {
			authorities.add(new SimpleGrantedAuthority(role.getRole()));
		}
		return authorities;
	}

	@Override
	public String getPassword() {
		return  passenger.getPassword();
	}

	@Override
	public String getUsername() {
		return passenger.getUserName();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return passenger.isEnabled();
	}

}
