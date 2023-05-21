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
@Table(name = "enugenres", uniqueConstraints = { @UniqueConstraint(columnNames = "genre") })
public class EnuGenreEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="genreId")
	private Long genreId;
	
	@Column(name="genre", length = 50, nullable = false)
	@NotEmpty(message = "Genre must not be empty.")
	private String genre;
	
	@Column(name="isActive",nullable = false)
	@ColumnDefault(value = "1")
	private boolean isActive;
}