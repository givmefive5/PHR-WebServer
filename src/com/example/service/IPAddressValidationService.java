package com.example.service;

import javax.servlet.http.HttpServletRequest;

public interface IPAddressValidationService {

	public String getIPAddressGivenServletRequest(HttpServletRequest request);

}
