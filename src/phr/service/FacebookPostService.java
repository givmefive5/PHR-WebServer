package phr.service;

import java.util.List;

import phr.exceptions.ServiceException;

public interface FacebookPostService {
	
	public List<String> getAllFacebookID (String AccessToken) throws ServiceException;

}
