package com.moviesdbapi.model;

import java.sql.Blob;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "MST_POSTER")
public class PosterEntity extends Auditable<String> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="posterId")
	private Long posterId;
	
	@Column(columnDefinition = "LONGBLOB NOT NULL")
	private Blob poster;
 	
	@Column(name="isActive",nullable = false)
	@ColumnDefault(value = "1")
	private boolean isActive;
}
