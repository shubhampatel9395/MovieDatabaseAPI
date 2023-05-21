package com.moviesdbapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class UserBasicDetailsEntity {
	@Column(nullable=false)
	private String firstName;
	
	@Column(nullable=false)
	private String lastName;
}
