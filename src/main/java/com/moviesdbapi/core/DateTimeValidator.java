package com.moviesdbapi.core;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

public class DateTimeValidator {
	public static boolean isValid(final String date) {
		boolean valid = false;

		try {
			LocalDate.parse(date, DateTimeFormatter.ofPattern("uuuu-M-d").withResolverStyle(ResolverStyle.STRICT));
			valid = true;
		} catch (DateTimeParseException e) {
			valid = false;
		}

		return valid;
	}
}