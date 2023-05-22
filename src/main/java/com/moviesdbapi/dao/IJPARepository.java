package com.moviesdbapi.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface IJPARepository<T,ID> extends JpaRepository<T, ID> {
}