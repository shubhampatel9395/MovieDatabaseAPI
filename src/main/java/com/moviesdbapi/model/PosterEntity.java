package com.moviesdbapi.model;

import java.sql.Blob;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "MST_POSTER")
// , uniqueConstraints = { @UniqueConstraint(columnNames = { "poster", "movieId" }) })
public class PosterEntity extends Auditable<String> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "posterId")
	private Long posterId;

	@NotNull
	@Column(columnDefinition = "LONGBLOB NOT NULL")
	private Blob poster;
	
	@NotEmpty(message = "Image type must not be empty.")
	@NotBlank(message = "Image type must not be blank.")
	private String type;

	@Valid
	@ManyToOne
	@JoinColumns(value = { @JoinColumn(name = "movieId", referencedColumnName = "movieId", nullable = false),
			@JoinColumn(name = "movieTitle", referencedColumnName = "title", nullable = false) })
	private MovieEntity movie;

	@Column(name = "isActive", nullable = false, columnDefinition = "BOOLEAN")
	@ColumnDefault(value = "1")
	private Boolean isActive = true;
}
