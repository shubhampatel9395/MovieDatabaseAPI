package com.moviesdbapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserBasicDetailsEntity {
	@Column(nullable = false)
	private String firstName;
	
	@Column(nullable = false)
	private String lastName;
}
