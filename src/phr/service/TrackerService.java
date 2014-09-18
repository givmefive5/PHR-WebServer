package phr.service;

import java.util.ArrayList;

import phr.exceptions.TrackerServiceException;

public interface TrackerService<T> {

	public void add(String accessToken, T object)
			throws TrackerServiceException;

	public void edit(String accessToken, T object)
			throws TrackerServiceException;

	public void delete(String accessToken, T object)
			throws TrackerServiceException;

	public ArrayList<T> getAll(String accessToken)
			throws TrackerServiceException;

	public Integer getEntryId(T object) throws TrackerServiceException;
}
