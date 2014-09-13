package phr.service;

import phr.exceptions.TrackerServiceException;

public interface TrackerService<T> {

	public void add(String username, T object) throws TrackerServiceException;

	public void edit(String username, T object) throws TrackerServiceException;

	public void delete(String username, T object)
			throws TrackerServiceException;
}
