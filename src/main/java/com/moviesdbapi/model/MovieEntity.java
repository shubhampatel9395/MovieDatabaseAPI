package com.moviesdbapi.model;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;

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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "MST_MOVIE", uniqueConstraints = { @UniqueConstraint(columnNames = {"title", "releaseDate"}) })
public class MovieEntity extends Auditable<String> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long movieId;
	
	@Column(nullable = false)
	private String title;
	
	@Column(length = 100)
	private String tagline;
	
	@Column(length = 1000, nullable = false)
	private String overview;
	
	@OneToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "moviegenremapping", joinColumns = {
			@JoinColumn(name="movieId", referencedColumnName = "movieId")
	}, inverseJoinColumns = {
			@JoinColumn(name="genreId", referencedColumnName = "genreId")
	})
	private List<EnuGenreEntity> genres;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns(value = {
			@JoinColumn(name="originCountryId", referencedColumnName = "countryId"),
			@JoinColumn(name="originCountryName", referencedColumnName = "country")
	})
	private EnuCountryEntity originCountryId;
	
	@OneToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "movielanguagemapping", joinColumns = {
			@JoinColumn(name="movieId", referencedColumnName = "movieId")
	}, inverseJoinColumns = {
			@JoinColumn(name="languageId", referencedColumnName = "languageId")
	})
	private List<EnuLanguageEntity> languages;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "moviepostermapping", joinColumns = {
			@JoinColumn(name="movieId", referencedColumnName = "movieId")
	}, inverseJoinColumns = {
			@JoinColumn(name="posterId", referencedColumnName = "posterId")
	})
	private List<PosterEntity> posters;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="budgetCurrencyId")
	private EnuCurrencyEntity budgetCurrency;
	
	@Column(columnDefinition = "DECIMAL(20,2)")
	private double budget;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="revenueCurrencyId")
	private EnuCurrencyEntity revenueCurrency;

	@Column(columnDefinition = "DECIMAL(20,2)")
	private double revenue;
	
	private String websiteURL;
	private String facebookPage;
	private String instagramPage;
	private String trailorLink;
	private String certificate;
	
	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Date releaseDate;
	
	@Column(nullable = false)
	@Temporal(TemporalType.TIME)
	private Time duration;
	
	@Column(columnDefinition = "DECIMAL(5,2)")
	private double avgRatings;
	
	@Column(name="isActive",nullable = false)
	@ColumnDefault(value = "1")
	private boolean isActive;
	
//	@Column(nullable = false)
//	private List<String> productionHouses;
}