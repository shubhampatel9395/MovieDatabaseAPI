package com.moviesdbapi.dao;

import com.moviesdbapi.model.EnuLanguageEntity;

public interface IEnuLanguageDAO extends IJPARepository<EnuLanguageEntity, Long> {
	public EnuLanguageEntity findOneByLanguage(String language);
}
