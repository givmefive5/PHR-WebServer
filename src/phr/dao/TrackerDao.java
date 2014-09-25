package phr.dao;

import java.util.ArrayList;

import phr.exceptions.DataAccessException;
import phr.exceptions.EntryNotFoundException;

public interface TrackerDao<T> {

	public int addReturnsEntryID(T object) throws DataAccessException;

	public void edit(T object) throws DataAccessException,
			EntryNotFoundException;

	public void delete(T object) throws DataAccessException,
			EntryNotFoundException;

	public ArrayList<T> getAll(String userAccessToken)
			throws DataAccessException;

	public Integer getEntryId(T object) throws DataAccessException;

}
