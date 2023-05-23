package com.moviesdbapi.model;

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

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "enucountry", uniqueConstraints = { @UniqueConstraint(columnNames = "country") })
@Entity
public class EnuCountryEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long countryId;

	@Column(nullable = false)
	private String country;

	@Column(name = "isActive", nullable = false, columnDefinition = "BOOLEAN")
	@ColumnDefault(value = "1")
	private Boolean isActive = true;

	public EnuCountryEntity(String country) {
		super();
		this.country = country;
	}

}