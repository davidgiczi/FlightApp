package com.giczi.david.flight.service;

import java.util.Base64;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EncoderService {

	@Bean
	public static PasswordEncoder getBCryptEncoder() {
	    return new BCryptPasswordEncoder();
		
	}
	
	public static String encodeByBase64(String rawPassword) {
		return Base64.getEncoder().encodeToString(rawPassword.getBytes());
	}
	
	public static String decodeByBase64(String encodedPassword) {
		return new String(Base64.getDecoder().decode(encodedPassword));
	}
}
