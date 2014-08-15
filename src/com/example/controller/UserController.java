package com.example.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.exceptions.ClientAuthenticationServiceException;
import com.example.exceptions.JSONConverterException;
import com.example.exceptions.UserServiceException;
import com.example.model.User;
import com.example.service.ClientAuthenticationService;
import com.example.service.UserService;
import com.example.tools.GSONConverter;
import com.example.tools.JSONParser;
import com.example.tools.JSONResponseCreator;
import com.example.tools.UUIDGenerator;

@Controller
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	ClientAuthenticationService clientAuthenticationService;

	@RequestMapping(value = "/user/validateLogin", method = RequestMethod.GET)
	public void validateLogin(HttpServletRequest request,
			HttpServletResponse response) throws IOException, JSONException {
		PrintWriter writer = response.getWriter();
		JSONObject jsonResponse = null;
		try {
			JSONObject json = GSONConverter.getJSONObjectFromReader(request
					.getReader());

			boolean isAuthorized = clientAuthenticationService
					.isFromAuthorizedClient(json);
			if (isAuthorized) {
				JSONObject data = JSONParser.getData(json);
				String username = data.getString("username");
				String hashedPassword = data.getString("hashedPassword");
				if (userService.isValidUser(new User(username, hashedPassword))) {
					JSONObject accessTokenJSON = new JSONObject();

					String accessToken = UUIDGenerator.generateUniqueString();
					userService.assignAccessToken(username, accessToken);

					accessTokenJSON.put("userAccessToken", accessToken);
					json = JSONResponseCreator.createJSONResponse("success",
							accessTokenJSON, null);
				}
			} else {
				jsonResponse = JSONResponseCreator.createJSONResponse("fail",
						null, "Not an authorized client, access denied.");
			}

		} catch (JSONConverterException | JSONException
				| ClientAuthenticationServiceException | UserServiceException e) {
			jsonResponse = JSONResponseCreator
					.createJSONResponse("fail", null,
							"Process cannot be completed, an error has occured in the web server");
			e.printStackTrace();
		}

		writer.write(jsonResponse.toString());
	}
}
