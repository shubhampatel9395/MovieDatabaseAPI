package com.moviesdbapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserBasicDetailsEntity {
	@Column(nullable = false)
	@NotEmpty(message = "First name must not be empty.")
	@NotBlank(message = "First name must not be blank.")
	private String firstName;
	
	@Column(nullable = false)
	@NotEmpty(message = "Last name must not be empty.")
	@NotBlank(message = "Last name must not be blank.")
	private String lastName;
}
