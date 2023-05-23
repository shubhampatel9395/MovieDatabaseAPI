package com.moviesdbapi.model.dto;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieDTO {
	private Long movieId;
	private String title;
	private String tagline;
	private String overview;
	private Long originCountryId;
	private String originCountryName;
	private Long budgetCurrencyId;
	private double budget;
	private Long revenueCurrencyId;
	private double revenue;
	private String websiteURL;
	private String facebookPage;
	private String instagramPage;
	private String trailorLink;
	private String certificate;
	private Date releaseDate;
	private Time duration;
	private double avgRatings;
	private Boolean isActive;
	private Timestamp createdDate;
	private String createdBy;
	private Timestamp lastModifiedDate;
	private String lastModifiedBy;
}
