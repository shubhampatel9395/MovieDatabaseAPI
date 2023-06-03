package com.moviesdbapi.validation;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.moviesdbapi.exception.InvalidDateException;

public class CustomDateDeserializer extends StdDeserializer<LocalDate> {
	private static final long serialVersionUID = -1549267653415263619L;
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	public CustomDateDeserializer() {
        this(null);
    }

	protected CustomDateDeserializer(Class<?> vc) {
		super(vc);
	}

	@Override
	public LocalDate deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
		String date = p.getText();
		try {
			return LocalDate.parse(date, formatter);
		} catch (DateTimeParseException e) {
			throw new InvalidDateException();
		}
	}

}
