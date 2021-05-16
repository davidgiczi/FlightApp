package com.giczi.david.flight.service;

import java.util.Locale;
import org.springframework.context.annotation.Bean;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Service
public class LangService {
	
	@Bean
	public static int getLanguageByLocale() {
		
		if(LocaleContextHolder.getLocale().equals(new Locale("hu"))) {
			return 1;
		}
		else if(LocaleContextHolder.getLocale().equals(new Locale("en"))) {
			return 2;
		}
		
		return -1;
	}

	
}
