package com.moviesdbapi.model;

import java.sql.Blob;

import org.hibernate.annotations.ColumnDefault;

import com.moviesdbapi.validation.NotNullEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
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
	@Column(name = "posterId")
	private Long posterId;

	@Column(columnDefinition = "LONGBLOB NOT NULL")
	private Blob poster;

	@Valid
	@NotNullEntity
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "movieId", nullable = false)
	private MovieEntity movieId;

	@Column(name = "isActive", nullable = false, columnDefinition = "BOOLEAN")
	@ColumnDefault(value = "1")
	private Boolean isActive = true;
}
