package com.moviesdbapi.model.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieCastDTO {
	private Long movieCastId;
	private Long movieId;
	private Long movieCastTypeId;
	private String movieCastType;
	private String originalFirstName;
	private String originalLastName;
	private String movieFirstName;
	private String movieLastName;
	private boolean isActive;
	private Timestamp createdDate;
	private String createdBy;
	private Timestamp lastModifiedDate;
	private String lastModifiedBy;
}