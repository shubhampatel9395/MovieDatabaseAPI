package com.moviesdbapi.validation;

import java.io.IOException;
import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.moviesdbapi.exception.InvalidTimeException;

public class CustomTimeDeserializer extends StdDeserializer<Time> {
	private static final long serialVersionUID = 4033041017844499198L;
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

	public CustomTimeDeserializer() {
		this(null);
	}

	protected CustomTimeDeserializer(Class<?> vc) {
		super(vc);
	}

	@Override
	public Time deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
		String time = p.getText();
		try {
			return Time.valueOf(LocalTime.parse(time, formatter));
		} catch (DateTimeParseException e) {
			throw new InvalidTimeException();
		}
	}

}
