package com.moviesdbapi.model;

import javax.validation.constraints.NotEmpty;

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
	private String role;
	
	@Column(name="isActive",nullable = false, columnDefinition = "BOOLEAN")
	@ColumnDefault(value = "1")
	private Boolean isActive = true;
	
	public EnuUserRoleEntity(String role) {
		this.role = role;
	}
}