package com.moviesdbapi.dao;

import com.moviesdbapi.model.EnuCurrencyEntity;

public interface IEnuCurrencyDAO extends IJPARepository<EnuCurrencyEntity, Long> {
	public EnuCurrencyEntity findOneByCurrencyName(String currencyName);
}
