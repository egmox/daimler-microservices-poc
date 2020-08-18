package com.egmox.spotfinder.management;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;

public class AbstractManagement {

	@Autowired
	MessageSource messageSource;

	@Autowired
	Environment env;

	public String getMessage(String messageCode) {
		messageSource.getMessage(messageCode, null, messageCode, Locale.getDefault());
		return null;
	}

	public String getVariable(String environmenrVariableCode) {
		return env.getProperty(environmenrVariableCode);
	}
}
