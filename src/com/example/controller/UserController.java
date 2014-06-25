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
import com.example.tools.GSONConverter;
import com.example.tools.JSONResponseCreator;

@Controller
public class UserController {

	@Autowired
	UserService userService;

	@RequestMapping(value = "/user/register")
	public void registerUser(HttpServletRequest request,
			HttpServletResponse response) throws IOException, JSONException {
		try {
			User userToAdd = GSONConverter.getGSONObjectFromReader(
					request.getReader(), User.class);

			userService.addUser(userToAdd);

			String message = "User " + userToAdd.getUsername()
					+ " has been successfully registered";
			JSONObject responseObj = JSONResponseCreator.createJSONResponse(
					JSONResponseCreator.STATUS_SUCCESS, null, message);

			response.getWriter().write(responseObj.toString());
		} catch (UsernameAlreadyExistsException e) {
			String message = "Username to add already exists.";
			JSONObject responseObj = JSONResponseCreator.createJSONResponse(
					JSONResponseCreator.STATUS_ERROR, null, message);
			response.getWriter().write(responseObj.toString());
		}
	}

	@RequestMapping(value = "/user/validate")
	public void validateUser(HttpServletRequest request,
			HttpServletResponse response) throws IOException, JSONException {

		User userToValidate = GSONConverter.getGSONObjectFromReader(
				request.getReader(), User.class);

		Boolean isValidUser = userService.isValidUser(userToValidate);
		JSONObject data = new JSONObject();
		data.append("isValid", isValidUser.booleanValue());
		JSONObject responseObj = JSONResponseCreator.createJSONResponse(
				JSONResponseCreator.STATUS_SUCCESS, data, null);
		response.getWriter().write(responseObj.toString());
	}
}
