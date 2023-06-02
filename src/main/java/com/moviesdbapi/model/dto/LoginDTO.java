package com.moviesdbapi.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO {
	@Email(message = "Invalid Email Address")
	@NotEmpty(message = "Email must not be empty.")
	@NotBlank(message = "Email must not be blank.")
	public String username;

	@NotEmpty(message = "Password must not be empty.")
	@NotBlank(message = "Password must not be blank.")
	public String password;
}