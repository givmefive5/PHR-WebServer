package com.example.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.example.service.IPAddressValidationService;

@Service("ipAddressValidationService")
public class IPAddressValidationServiceImpl implements
		IPAddressValidationService {

	@Override
	public String getIPAddressGivenServletRequest(HttpServletRequest request) {
		String ipAddress = request.getHeader("X-FORWARDED-FOR");
		if (ipAddress == null)
			ipAddress = request.getRemoteAddr();
		return ipAddress;
	}

}
