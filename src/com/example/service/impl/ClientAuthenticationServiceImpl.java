package com.example.service.impl;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.ClientAuthenticationDao;
import com.example.exceptions.ClientAuthenticationServiceException;
import com.example.exceptions.DataAccessException;
import com.example.service.ClientAuthenticationService;
import com.example.tools.JSONParser;

@Service("clientAuthenticationService")
public class ClientAuthenticationServiceImpl implements
		ClientAuthenticationService {

	@Autowired
	ClientAuthenticationDao clientAuthenticationDao;

	@Override
	public boolean isFromAuthorizedClient(JSONObject json)
			throws ClientAuthenticationServiceException {

		try {
			String clientID = JSONParser.getClientID(json);
			String clientPassword = JSONParser.getClientPassword(json);
			return clientAuthenticationDao.isAuthorizedClient(clientID,
					clientPassword);
		} catch (DataAccessException | JSONException e) {
			throw new ClientAuthenticationServiceException(
					"Cannot process client authentication service request", e);
		}
	}

}
