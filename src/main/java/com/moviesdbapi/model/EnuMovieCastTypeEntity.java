package com.moviesdbapi.model;

import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
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
	@Column(name="movieCastTypeId")
	private Long movieCastTypeId;
	
	@Column(name="movieCastType", nullable = false)
	@NotEmpty(message = "Cast type must not be empty.")
	private String movieCastType;
	
	@Column(name="isActive",nullable = false)
	@ColumnDefault(value = "1")
	private boolean isActive;
}