package com.moviesdbapi.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnuUserRoleDTO {
	private Long userRoleId;
	private String role;
	private boolean isActive;
}