package com.moviesdbapi.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnuCountryDTO {
	private Long countryId;
	private String country;
	private boolean isActive;
}