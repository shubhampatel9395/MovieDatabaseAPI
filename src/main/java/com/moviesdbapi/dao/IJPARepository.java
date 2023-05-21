package com.moviesdbapi.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

public interface IJPARepository<T,ID> extends JpaRepository<T, ID> {
	
	public List<T> findByFieldValue(String fieldName, Object fieldValue);

	public default List<T> findByNamedParameters(MapSqlParameterSource paramSource) {
		throw new UnsupportedOperationException();
	}
	
	public default boolean softDelete(ID id) {
		return false;
	}
	
	public default boolean activate(ID id) {
		return false;
	}
}