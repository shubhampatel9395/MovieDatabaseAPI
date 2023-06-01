package com.moviesdbapi.model.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {
	private Long reviewId;
	private Long movieId;
	private String movieTitle;
	private Long userId;
	private String userFirstName;
	private String userLastName;
	private Double rating;
	private String reviewTitle;
	private String reviewContent;
	private Boolean isActive;
	private Timestamp createdDate;
	private String createdBy;
	private Timestamp lastModifiedDate;
	private String lastModifiedBy;
}
