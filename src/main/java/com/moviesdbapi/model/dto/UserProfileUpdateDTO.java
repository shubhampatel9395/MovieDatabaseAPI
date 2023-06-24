package com.moviesdbapi.model.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.moviesdbapi.model.EnuCountryEntity;
import com.moviesdbapi.model.UserBasicDetailsEntity;
import com.moviesdbapi.validation.CustomDateDeserializer;
import com.moviesdbapi.validation.NotBlankString;
import com.moviesdbapi.validation.NotNullEntity;

import jakarta.validation.Valid;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserProfileUpdateDTO {
	@Valid
	@NotNullEntity
	private UserBasicDetailsEntity basicDetails;

	@NotBlankString
	private String gender;

	@PastOrPresent
	@Valid
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	@JsonFormat(pattern = "dd/MM/yyyy")
	@JsonDeserialize(using = CustomDateDeserializer.class)
	private LocalDate dob;

	@Valid
	private EnuCountryEntity country;
}
