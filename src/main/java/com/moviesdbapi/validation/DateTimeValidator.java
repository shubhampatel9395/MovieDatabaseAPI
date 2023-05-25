package com.moviesdbapi.validation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeParseException;

public class DateTimeValidator {
	public static boolean isValid(final String date) {
		boolean valid = false;

		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			sdf.setLenient(false);
			sdf.parse(date);
			valid = true;
		} catch (DateTimeParseException e) {
			valid = false;
		} catch (ParseException e) {
			valid = false;
		}

		return valid;
	}
}