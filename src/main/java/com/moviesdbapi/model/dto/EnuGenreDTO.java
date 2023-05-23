package com.moviesdbapi.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnuGenreDTO {
	private Long genreId;
	private String genre;
	private Boolean isActive;
}