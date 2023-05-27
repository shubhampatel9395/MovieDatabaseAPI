package com.moviesdbapi.model;

import java.sql.Date;
import java.sql.Time;
import java.util.Set;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.moviesdbapi.validation.NotNullEntity;
import com.moviesdbapi.validation.NotNullEntityCollection;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "MST_MOVIE", uniqueConstraints = { @UniqueConstraint(columnNames = { "title", "releaseDate" }) })
public class MovieEntity extends Auditable<String> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long movieId;

	@Column(nullable = false)
	@NotEmpty(message = "Movie title must not be empty.")
	@NotBlank(message = "Movie title must not be blank.")
	private String title;

	@Column(length = 100)
	@Size(min = 5, max = 100, message = "Movie tagline must be within 5 to 100 characters.")
	private String tagline;

	@Column(length = 10000, nullable = false)
	@Size(max = 10000, message = "Movie overview must be within 10000 characters.")
	@NotEmpty(message = "Movie overview must not be empty.")
	@NotBlank(message = "Movie overview must not be blank.")
	private String overview;

	@NotNullEntityCollection
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "moviegenremapping", joinColumns = {
			@JoinColumn(name = "movieId", referencedColumnName = "movieId") }, inverseJoinColumns = {
					@JoinColumn(name = "genreId", referencedColumnName = "genreId") })
	private Set<EnuGenreEntity> genres;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns(value = { @JoinColumn(name = "originCountryId", referencedColumnName = "countryId"),
			@JoinColumn(name = "originCountryName", referencedColumnName = "country") })
	private EnuCountryEntity originCountry;

	@NotNullEntityCollection
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "movielanguagemapping", joinColumns = {
			@JoinColumn(name = "movieId", referencedColumnName = "movieId") }, inverseJoinColumns = {
					@JoinColumn(name = "languageId", referencedColumnName = "languageId") })
	private Set<EnuLanguageEntity> languages;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "moviepostermapping", joinColumns = {
			@JoinColumn(name = "movieId", referencedColumnName = "movieId") }, inverseJoinColumns = {
					@JoinColumn(name = "posterId", referencedColumnName = "posterId") })
	private Set<PosterEntity> posters;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "currencyId")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
//  For lazy initialization of objects
	private EnuCurrencyEntity currency;

	@DecimalMin(value = "0", message = "Movie budget must be positive.")
	@Digits(integer = 20, fraction = 2, message = "Double range exceeds.")
	@Column(columnDefinition = "DECIMAL(20,2)")
	private double budget;

	@DecimalMin(value = "0", message = "Movie revenue must be positive.")
	@Digits(integer = 20, fraction = 2, message = "Double range exceeds.")
	@Column(columnDefinition = "DECIMAL(20,2)")
	private double revenue;

	@URL
	private String websiteURL;

	@URL
	private String facebookPage;

	@URL
	private String instagramPage;

	@URL
	private String trailorLink;

	private String certificate;

	@NotNullEntity
	@Column(nullable = false)
	@PastOrPresent
	@Valid
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date releaseDate;

	@NotNullEntity
	@Valid
	@Column(nullable = false)
	@Temporal(TemporalType.TIME)
	private Time duration;

	@Column(columnDefinition = "DECIMAL(5,2)")
	private double avgRatings;

	@Column(name = "isActive", nullable = false, columnDefinition = "BOOLEAN")
	@ColumnDefault(value = "1")
	private Boolean isActive = true;

//	@Column(nullable = false)
//	private List<String> productionHouses;
}