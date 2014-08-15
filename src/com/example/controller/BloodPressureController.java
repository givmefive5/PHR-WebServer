package com.example.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.service.ClientAuthenticationService;

@Controller
public class BloodPressureController {

	// @Autowired
	// BloodPressureService bloodPressureService;
	@Autowired
	ClientAuthenticationService clientAuthenticationService;

	@RequestMapping(value = "/tracker/addBloodPressure", method = RequestMethod.GET)
	public void addBloodPressure(HttpServletRequest request,
			HttpServletResponse response) {

	}

}
