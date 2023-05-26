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
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "enucurrency", uniqueConstraints = { @UniqueConstraint(columnNames = "currencyName") })
public class EnuCurrencyEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "currencyId")
	private Long currencyId;

	@Column(name = "currencyName", length = 50, nullable = false)
	@Size(max = 50, message = "Currency Name must be within 50 characters.")
	@NotEmpty(message = "Currency Name must not be empty.")
	@NotBlank(message = "Currency Name must not be blank.")
	private String currencyName;

	@Column(name = "currencySymbol", length = 5)
	@Size(max = 5, message = "Currency symbol must be within 50 characters.")
	private String currencySymbol;

	@Column(name = "isActive", nullable = false, columnDefinition = "BOOLEAN")
	@ColumnDefault(value = "1")
	private Boolean isActive = true;

	public EnuCurrencyEntity(
			@Size(max = 50, message = "Currency Name must be within 50 characters.") @NotEmpty(message = "Currency Name must not be empty.") @NotBlank(message = "Currency Name must not be blank.") String currencyName) {
		super();
		this.currencyName = currencyName;
	}

}