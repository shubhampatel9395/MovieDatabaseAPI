package com.moviesdbapi.model.dto;

import com.moviesdbapi.validation.NotBlankString;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieCastCreateDTO {
	@NotEmpty(message = "Cast type must not be empty.")
	@NotBlank(message = "Cast type must not be blank.")
	private String movieCastType;

	@NotEmpty(message = "Original first name must not be empty.")
	@NotBlank(message = "Original first name must not be blank.")
	private String originalFirstName;

	@NotEmpty(message = "Original last name must not be empty.")
	@NotBlank(message = "Original last name must not be blank.")
	private String originalLastName;

	@NotBlankString(message = "Movie first name must not be blank.")
	private String movieFirstName;

	@NotBlankString(message = "Movie last name must not be blank.")
	private String movieLastName;
}