package com.moviesdbapi.model.dto;

import java.sql.Blob;
import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PosterDTO {
	private Long posterId;
	private Blob poster;
	private boolean isActive;
	private Timestamp createdDate;
	private String createdBy;
	private Timestamp lastModifiedDate;
	private String lastModifiedBy;
}
