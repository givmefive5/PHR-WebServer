package phr.sns.datamining.dao;

import java.util.List;

import phr.exceptions.DataAccessException;

public interface HealthCorpusDao {

	List<String> getFoodWords() throws DataAccessException;

	List<String> getActivityWords() throws DataAccessException;

	List<String> getRestaurantNames() throws DataAccessException;

	List<String> getSportsEstablishmentNames() throws DataAccessException;
}
