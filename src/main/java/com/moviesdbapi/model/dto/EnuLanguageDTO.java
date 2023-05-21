package com.moviesdbapi.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnuLanguageDTO {
	private Long languageId;
	private String language;
	private boolean isActive;
}
