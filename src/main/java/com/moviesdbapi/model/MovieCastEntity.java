package com.moviesdbapi.model;

import org.hibernate.annotations.ColumnDefault;

import com.moviesdbapi.validation.NotNullEntity;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "MST_MOVIECAST")
public class MovieCastEntity extends Auditable<String> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "movieCastId")
	private Long movieCastId;

	@Valid
	@NotNullEntity
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns(value = {
			@JoinColumn(name = "movieId", referencedColumnName = "movieId", nullable = false),
			@JoinColumn(name = "movieTitle", referencedColumnName = "title", nullable = false) })
	private MovieEntity movie;

	@Valid
	@NotNullEntity
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumns(value = {
			@JoinColumn(name = "movieCastTypeId", referencedColumnName = "movieCastTypeId", nullable = false),
			@JoinColumn(name = "movieCastType", referencedColumnName = "movieCastType", nullable = false) })
	private EnuMovieCastTypeEntity castType;

	@Valid
	@NotNullEntity
	@AttributeOverrides({
			@AttributeOverride(name = "firstName", column = @Column(name = "originalFirstName", nullable = false)),
			@AttributeOverride(name = "lastName", column = @Column(name = "originalLastName", nullable = false)) })
	@Embedded
	private UserBasicDetailsEntity originalNames;

	@AttributeOverrides({
			@AttributeOverride(name = "firstName", column = @Column(name = "movieFirstName", nullable = true)),
			@AttributeOverride(name = "lastName", column = @Column(name = "movieLastName", nullable = true)) })
	@Embedded
	private UserBasicDetailsEntity movieNames;

	@Column(name = "isActive", nullable = false, columnDefinition = "BOOLEAN")
	@ColumnDefault(value = "1")
	private Boolean isActive = true;
}