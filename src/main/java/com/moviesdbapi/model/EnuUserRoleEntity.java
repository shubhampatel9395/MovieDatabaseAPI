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
@Table(name = "enuuserrole", uniqueConstraints = { @UniqueConstraint(columnNames = "role") })
public class EnuUserRoleEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="userRoleId")
	private Long userRoleId;
	
	@Column(name="role", length = 50, nullable = false)
	@NotEmpty(message = "User role must not be empty.")
	@NotBlank(message = "User role must not be blank.")
	private String role;
	
	@Column(name="isActive",nullable = false, columnDefinition = "BOOLEAN")
	@ColumnDefault(value = "1")
	private Boolean isActive = true;
	
	public EnuUserRoleEntity(String role) {
		this.role = role;
	}
}