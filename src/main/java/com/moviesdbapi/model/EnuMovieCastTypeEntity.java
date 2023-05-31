package com.moviesdbapi.model;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "enumoviecasttype", uniqueConstraints = { @UniqueConstraint(columnNames = "movieCastType") })
public class EnuMovieCastTypeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "movieCastTypeId")
	private Long movieCastTypeId;

	@Column(name = "movieCastType", nullable = false)
	@NotBlank(message = "Cast type must not be blank.")
	@NotEmpty(message = "Cast type must not be empty.")
	private String movieCastType;

	@Column(name = "isActive", nullable = false, columnDefinition = "BOOLEAN")
	@ColumnDefault(value = "1")
	private Boolean isActive = true;

	public EnuMovieCastTypeEntity(
			@NotBlank(message = "Cast type must not be blank.") @NotEmpty(message = "Cast type must not be empty.") String movieCastType) {
		super();
		this.movieCastType = movieCastType;
	}

}