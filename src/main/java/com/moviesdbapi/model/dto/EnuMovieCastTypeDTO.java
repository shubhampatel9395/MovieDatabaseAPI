package com.moviesdbapi.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnuMovieCastTypeDTO {
	private Long movieCastTypeId;
	private String movieCastType;
	private Boolean isActive;
}