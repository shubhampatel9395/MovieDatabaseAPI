package com.moviesdbapi.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieCastUpdateDTO extends MovieCastCreateDTO {
	@NotNull
	private Long movieCastId;
}