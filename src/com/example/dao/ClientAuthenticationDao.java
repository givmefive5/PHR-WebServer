package com.example.dao;

import com.example.exceptions.DataAccessException;

public interface ClientAuthenticationDao {

	boolean isAuthorizedClient(String clientID, String clientPassword)
			throws DataAccessException;

}
