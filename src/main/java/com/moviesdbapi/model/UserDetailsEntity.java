package com.moviesdbapi.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.moviesdbapi.validation.CustomDateDeserializer;
import com.moviesdbapi.validation.NotBlankString;
import com.moviesdbapi.validation.NotNullEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "MST_USERDETAILS", uniqueConstraints = { @UniqueConstraint(columnNames = "email") })
public class UserDetailsEntity extends Auditable<String> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "userId")
	private Long userId;

	@Valid
	@NotNullEntity
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

	@NotBlankString
	private String gender;

	@PastOrPresent
	@Valid
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	@JsonFormat(pattern = "dd/MM/yyyy")
	@JsonDeserialize(using = CustomDateDeserializer.class)
	private LocalDate dob;

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

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private Set<ReviewEntity> reviews = new HashSet<>();
	
	public void addReview(ReviewEntity entity) {
		this.reviews.add(entity);
		entity.setUser(this);
	}
}