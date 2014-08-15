package com.example.service;

import org.json.JSONObject;

import com.example.exceptions.ClientAuthenticationServiceException;

public interface ClientAuthenticationService {

	public boolean isFromAuthorizedClient(JSONObject json)
			throws ClientAuthenticationServiceException;
}
