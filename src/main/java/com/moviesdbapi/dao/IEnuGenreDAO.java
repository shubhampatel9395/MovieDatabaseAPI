package com.moviesdbapi.dao;

import com.moviesdbapi.model.EnuGenreEntity;

public interface IEnuGenreDAO extends IJPARepository<EnuGenreEntity, Long> {
	public EnuGenreEntity findOneByGenre(String genre);
}
