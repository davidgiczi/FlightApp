package com.giczi.david.flight.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.giczi.david.flight.service.EncoderService;
import com.giczi.david.flight.service.PassengerService;
import com.giczi.david.flight.service.PassengerServiceImpl;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	
	private PassengerService passengerService;
	

	@Autowired
	 public void setPassengerService(PassengerService passengerService) {
		this.passengerService = passengerService;
	}


	@Bean
	  public UserDetailsService userDetailsService() {
		 return new PassengerServiceImpl();
	 }
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.authorizeRequests()
				.antMatchers("/login*").permitAll()
				.antMatchers("/flight/reg").permitAll()
				.antMatchers("/flight/activation/**").permitAll()
				.antMatchers("/flight-js/**", "/flight-css/**").permitAll()
				.antMatchers("/console/**").permitAll()
				.antMatchers("/flight/registration").permitAll()
				.antMatchers("/admin/**").hasRole("ADMIN")
				.anyRequest().authenticated()
			.and()
			.formLogin()
				.loginPage("/login")
				.defaultSuccessUrl("/flight/order", true)
				.permitAll()
			.and()
			.logout()
			.logoutUrl("/logout")
			.invalidateHttpSession(true)
			.deleteCookies("JSESSIONID")
			.addLogoutHandler(new CustomLogoutHandler(passengerService))
			.logoutSuccessUrl("/login?logout")
			.permitAll();
		
		http.headers().frameOptions().disable();
	}
	
	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
     	
		//auth.userDetailsService(userDetailsService()).passwordEncoder(EncoderService.getBCryptEncoder());
		auth.userDetailsService(userDetailsService()).passwordEncoder(new PasswordEncoder() {
			
			@Override
			public boolean matches(CharSequence rawPassword, String encodedPassword) {
				
				return rawPassword.equals(EncoderService.decodeByBase64(encodedPassword));
			}
			
			@Override
			public String encode(CharSequence rawPassword) {
				
				return EncoderService.encodeByBase64(String.valueOf(rawPassword));
			}
		});
       
    }
	

}
