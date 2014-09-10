package com.example.dao;

import com.example.exceptions.DataAccessException;
import com.example.model.BloodSugar;

public interface BloodSugarDao {

	public void addBloodSugar(BloodSugar bloodSugar) throws DataAccessException;

	public Integer getIdFromDatabase(BloodSugar bloodSugar)
			throws DataAccessException;

}
