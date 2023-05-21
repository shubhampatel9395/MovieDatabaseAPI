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
@Table(name = "enulanguage", uniqueConstraints = { @UniqueConstraint(columnNames = "language") })
public class EnuLanguageEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="languageId")
	private Long languageId;
	
	@Column(name="language", length = 50, nullable = false)
	@NotEmpty(message = "Language must not be empty.")
	private String language;
	
	@Column(name="isActive",nullable = false)
	@ColumnDefault(value = "1")
	private boolean isActive;
}
