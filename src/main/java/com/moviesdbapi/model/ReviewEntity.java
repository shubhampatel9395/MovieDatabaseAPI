package com.moviesdbapi.model;

import org.hibernate.annotations.ColumnDefault;

import com.moviesdbapi.validation.NotBlankString;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "MST_REVIEW", uniqueConstraints = { @UniqueConstraint(columnNames = { "userId", "movieId" }) })
public class ReviewEntity extends Auditable<String> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long reviewId;

	@Valid
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId", referencedColumnName = "userId", nullable = false)
	private UserDetailsEntity user;

	@Valid
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "movieId", referencedColumnName = "movieId", nullable = false)
	private MovieEntity movie;

	@DecimalMin(value = "0", message = "Movie rating must be between 0 to 10.")
	@DecimalMax(value = "10", message = "Movie rating must be between 0 to 10.")
	@Digits(integer = 4, fraction = 2, message = "Double range exceeds.")
	@Column(columnDefinition = "DECIMAL(4,2) NOT NULL")
	@NotNull
	private Double rating;

	@Column(length = 500)
	@NotBlankString(message = "Movie's review title must not be blank.")
	@Size(max = 500, message = "Movie's review title must be within 500 characters.")
	private String reviewTitle;

	@Column(length = 5000)
	@NotBlankString(message = "Movie's review content must not be blank.")
	@Size(max = 5000, message = "Movie's review content must be within 5000 characters.")
	private String reviewContent;

	@Column(name = "isActive", nullable = false, columnDefinition = "BOOLEAN")
	@ColumnDefault(value = "1")
	private Boolean isActive = true;
}