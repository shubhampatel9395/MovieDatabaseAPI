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
@Table(name = "enulanguage", uniqueConstraints = { @UniqueConstraint(columnNames = "language") })
public class EnuLanguageEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="languageId")
	private Long languageId;
	
	@Column(name="language", length = 50, nullable = false)
	@NotEmpty(message = "Language must not be empty.")
	@NotBlank(message = "Language must not be blank.")
	private String language;
	
	@Column(name="isActive",nullable = false, columnDefinition = "BOOLEAN")
	@ColumnDefault(value = "1")
	private Boolean isActive = true;

	public EnuLanguageEntity(
			@NotEmpty(message = "Language must not be empty.") @NotBlank(message = "Language must not be blank.") String language) {
		this.language = language;
	}
}
