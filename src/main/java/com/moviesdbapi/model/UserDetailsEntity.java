package com.moviesdbapi.model;

import java.util.Date;

import org.hibernate.annotations.ColumnDefault;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.moviesdbapi.validation.NotNullEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "MST_USERDETAILS", uniqueConstraints = { @UniqueConstraint(columnNames = "email") })
public class UserDetailsEntity extends Auditable<String> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "userId")
	private Long userId;

	@Valid
	@Embedded
	private UserBasicDetailsEntity basicDetails;

	@Column(nullable = false)
	@Email(message = "Invalid Email Address")
	@NotEmpty(message = "Email must not be empty.")
	@NotBlank(message = "Email must not be blank.")
	private String email;

	@Column(nullable = false)
	@NotEmpty(message = "Password must not be empty.")
	@NotBlank(message = "Password must not be blank.")
	private String password;

	private String gender;

	@JsonSerialize(as = Date.class)
	@JsonFormat(pattern = "dd/MM/yyyy", shape = Shape.STRING)
	@PastOrPresent
	@Temporal(TemporalType.DATE)
	private Date dob;

	@Valid
	@ManyToOne
	@JoinColumns(value = { @JoinColumn(name = "countryId", referencedColumnName = "countryId"),
			@JoinColumn(name = "countryName", referencedColumnName = "country") })
	private EnuCountryEntity country;

	@Valid
	@NotNullEntity
	@ManyToOne
	@JoinColumns(value = { @JoinColumn(name = "userRoleId", referencedColumnName = "userRoleId", nullable = false),
			@JoinColumn(name = "userRole", referencedColumnName = "role", nullable = false) })
	private EnuUserRoleEntity userRole;

	@Column(name = "isActive", nullable = false, columnDefinition = "BOOLEAN")
	@ColumnDefault(value = "1")
	private Boolean isActive = true;
}