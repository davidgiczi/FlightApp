package com.giczi.david.flight.exception;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.giczi.david.flight.service.LangService;


@ControllerAdvice
public class ExceptionGeneral {

	@ExceptionHandler
	public String exceptionHandler(HttpServletRequest request, Exception ex, Model model) {
		
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		
		model.addAttribute("status", Integer.valueOf(status.toString()));
		switch(LangService.getLanguageByLocale()) {
		case 1:
			model.addAttribute("error", "Rendellenes működés adódott");
		break;
		case 2:
			model.addAttribute("error", "An exception occured");
		break;
		default:
			model.addAttribute("error", "Rendellenes működés adódott");
		}
		model.addAttribute("message", ex.getMessage());
		model.addAttribute("timestamp", System.currentTimeMillis());
		
		return "error";
	}
	
	
}
