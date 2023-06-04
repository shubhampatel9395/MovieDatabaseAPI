package com.moviesdbapi.dao;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.moviesdbapi.model.PosterEntity;

public interface CustomizedPosterDAO {
	public List<PosterEntity> findByFieldValue(String fieldName, Object fieldValue);

	public List<PosterEntity> findByNamedParameters(MapSqlParameterSource paramSource);

	public default boolean softDelete(Long id) {
		return false;
	}

	public default boolean activate(Long id) {
		return false;
	}
}
