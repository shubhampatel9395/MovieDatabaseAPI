package com.moviesdbapi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moviesdbapi.core.DateTimeValidator;
import com.moviesdbapi.dao.IEnuCountryDAO;
import com.moviesdbapi.dao.IEnuUserRoleDAO;
import com.moviesdbapi.dao.IUserDetailsDAO;
import com.moviesdbapi.exception.DuplicateEmailException;
import com.moviesdbapi.exception.InvalidCountryException;
import com.moviesdbapi.exception.InvalidDateException;
import com.moviesdbapi.exception.InvalidPasswordException;
import com.moviesdbapi.exception.InvalidUserRoleException;
import com.moviesdbapi.model.EnuCountryEntity;
import com.moviesdbapi.model.EnuUserRoleEntity;
import com.moviesdbapi.model.UserDetailsEntity;
import com.moviesdbapi.model.dto.UserDetailsDTO;
import com.moviesdbapi.service.IUserDetailsService;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserDetailsService implements IUserDetailsService {

	@Autowired
	IUserDetailsDAO userDetailsDAO;

	@Autowired
	IEnuUserRoleDAO enuUserRoleDAO;

	@Autowired
	IEnuCountryDAO enuCountryDAO;

//	@PersistenceContext
//	private EntityManager entityManager;

	@Override
	public List<UserDetailsEntity> findAll() {
		return userDetailsDAO.findAll();
	}

	@Override
	public List<UserDetailsDTO> findAllActive() {
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("isActive", true);
		return userDetailsDAO.findByNamedParameters(paramSource);
	}

	@Override
	public UserDetailsEntity findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserDetailsEntity> findByFieldValue(String fieldName, Object fieldValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserDetailsEntity> findByNamedParameters(MapSqlParameterSource paramSource) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserDetailsEntity insert(UserDetailsEntity entity) throws DuplicateEmailException, InvalidPasswordException,
			InvalidCountryException, InvalidUserRoleException, InvalidCountryException, InvalidDateException {
		// Unique Email
		List<UserDetailsEntity> emailDTO = userDetailsDAO.findByEmail(entity.getEmail());
		if (emailDTO.isEmpty() != true) {
			throw new DuplicateEmailException();
		}

		// Password Rules
		String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";
		boolean isValidPassword = entity.getPassword().matches(pattern);
		if (isValidPassword == false) {
			throw new InvalidPasswordException();
		}

		// Set Password
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(entity.getPassword());
		entity.setPassword(encodedPassword);

		// Set User Role
		List<EnuUserRoleEntity> userRoles = enuUserRoleDAO.findByRole(entity.getUserRole().getRole());
		if (userRoles.isEmpty() == true) {
			throw new InvalidUserRoleException();
		} else {
			entity.setUserRole(DataAccessUtils.singleResult(userRoles));
		}

		// Set Country
		if (entity.getCountry() != null) {
			List<EnuCountryEntity> countries = enuCountryDAO.findByCountry(entity.getCountry().getCountry());

			if (countries.isEmpty() == true) {
				throw new InvalidCountryException();
			} else {
				entity.setCountry(DataAccessUtils.singleResult(countries));
			}
		}

		// Check DOB
		if (entity.getDob() != null) {
			if (DateTimeValidator.isValid(entity.getDob().toString()) == false) {
				throw new InvalidDateException();
			}
		}

		return userDetailsDAO.save(entity);
	}

	@Override
	public UserDetailsEntity update(UserDetailsEntity entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(UserDetailsEntity entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean activate(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean softDelete(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

}
