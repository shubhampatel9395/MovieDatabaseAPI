package com.moviesdbapi.dao;

import java.util.List;

import com.moviesdbapi.model.EnuCountryEntity;

public interface IEnuCountryDAO extends IJPARepository<EnuCountryEntity, Long> {
	public List<EnuCountryEntity> findByCountry(String country);
	public EnuCountryEntity findOneByCountry(String country);
}