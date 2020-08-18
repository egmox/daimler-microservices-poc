package com.egmox.poc.management;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

public class AbstractManagement {
	@Autowired
	MessageSource messageSource;
	
	public String getMessage(String messageCode) {
		messageSource.getMessage(messageCode, null, messageCode, Locale.getDefault());
		return null;
	}
	
}
