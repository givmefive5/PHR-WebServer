package com.example.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HealthProfessionalUserController {

	@RequestMapping(value = "/loginPageCreator")
	public String loginPageCreator(HttpServletRequest request,
			HttpServletResponse response) {

		return "login";
	}

	@RequestMapping(value = "/loginValidation", method = RequestMethod.POST)
	public String loginValidation(HttpServletRequest request,
			HttpServletResponse response) {

		return "home";
	}

	@RequestMapping(value = "/register")
	public String register() {
		return "register";
	}
}
