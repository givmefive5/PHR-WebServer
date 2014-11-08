package phr.service;

import java.sql.Timestamp;
import java.util.List;

import phr.exceptions.ServiceException;

public interface FacebookPostService {

	public List<String> getAllFacebookID(String AccessToken)
			throws ServiceException;

	public Timestamp getLatestPostTimestamp(String accessToken)
			throws ServiceException;
}
