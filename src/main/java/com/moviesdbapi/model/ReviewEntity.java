package com.moviesdbapi.model;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "MST_REVIEW")
public class ReviewEntity extends Auditable<String> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long reviewId;
	
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name = "userId", referencedColumnName = "userId", nullable = false),
		@JoinColumn(name = "userFirstName", referencedColumnName = "firstName", nullable = false),
		@JoinColumn(name = "userLastName", referencedColumnName = "lastName", nullable = false)
	})
	private UserDetailsEntity user;
	
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name = "movieId", referencedColumnName = "movieId", nullable = false),
		@JoinColumn(name = "movieTitle", referencedColumnName = "title", nullable = false)
	})
	private MovieEntity movie;
	
	@Column(columnDefinition = "DECIMAL(5,2) NOT NULL")
	private double rating;
	
	@Column(length = 100)
	private String reviewTitle;
	
	@Column(length = 5000)
	private String reviewContent;
	
	@Column(name="isActive",nullable = false)
	@ColumnDefault(value = "1")
	private boolean isActive;
}