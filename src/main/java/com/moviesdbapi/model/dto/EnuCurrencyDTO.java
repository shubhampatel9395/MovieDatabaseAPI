package com.moviesdbapi.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnuCurrencyDTO {
	private Long currencyId;
	private String currencyName;
	private String currencySymbol;
	private boolean isActive;
}