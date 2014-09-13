package phr.dao;

import java.util.ArrayList;

import phr.exceptions.DataAccessException;

public interface TrackerDao<T> {

	public void add(T object) throws DataAccessException;

	public void edit(T object) throws DataAccessException;

	public void delete(T object) throws DataAccessException;

	public ArrayList<T> getAll(String username) throws DataAccessException;

	public Integer getEntryId(T object) throws DataAccessException;

}
