package com.moviesdbapi.model.dto;

import java.sql.Date;
import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsDTO {
	private Long userId;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String gender;
	private Date dob;
	private Long countryId;
	private String countryName;
	private Long userRoleId;
	private String userRole;
	private boolean isActive;
	private Timestamp createdDate;
	private String createdBy;
	private Timestamp lastModifiedDate;
	private String lastModifiedBy;
}
