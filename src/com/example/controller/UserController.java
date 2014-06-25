package com.example.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.exceptions.UsernameAlreadyExistsException;
import com.example.model.User;
import com.example.service.UserService;
import com.example.tools.JSONConverter;
import com.example.tools.JSONMessageCreator;

@Controller
public class UserController {

	@Autowired
	UserService userService;

	@RequestMapping(value = "/user/register")
	public void registerUser(HttpServletRequest request,
			HttpServletResponse response) throws IOException, JSONException {
		try {
			User userToAdd = JSONConverter.getGSONObjectFromReader(
					request.getReader(), User.class);

			userService.addUser(userToAdd);

			JSONObject messageJson = JSONMessageCreator
					.createJSONMessage("User has been successfully registered");
			response.getWriter().write(messageJson.toString());
		} catch (UsernameAlreadyExistsException e) {
			JSONObject errorJson = JSONMessageCreator
					.createJSONError("username already exists");
			response.getWriter().write(errorJson.toString());
		}
	}

	@RequestMapping(value = "/user/validate")
	public void validateUser(HttpServletRequest request,
			HttpServletResponse response) throws IOException, JSONException {

		User userToValidate = JSONConverter.getGSONObjectFromReader(
				request.getReader(), User.class);

		Boolean isValidUser = userService.isValidUser(userToValidate);
		JSONObject messageJson = JSONMessageCreator
				.createJSONMessage(isValidUser.toString());
		response.getWriter().write(messageJson.toString());
	}
}
