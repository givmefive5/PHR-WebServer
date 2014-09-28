package phr.service;

import java.util.ArrayList;

import phr.exceptions.EntryNotFoundException;
import phr.exceptions.ServiceException;

public interface TrackerService<T> {

	public int addReturnEntryID(String accessToken, T object)
			throws ServiceException;

	public void edit(String accessToken, T object)
			throws ServiceException, EntryNotFoundException;

	public void delete(String accessToken, T object)
			throws ServiceException, EntryNotFoundException;

	public ArrayList<T> getAll(String accessToken)
			throws ServiceException;

	public Integer getEntryId(T object) throws ServiceException;
}
