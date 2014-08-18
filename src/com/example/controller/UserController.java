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

import com.example.dao.UserDao;
import com.example.exceptions.ClientAuthenticationServiceException;
import com.example.exceptions.JSONConverterException;
import com.example.exceptions.UserServiceException;
import com.example.exceptions.UsernameAlreadyExistsException;
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

	@Autowired
	UserDao userDao;

	@RequestMapping(value = "/user/validateLogin")
	public void validateLogin(HttpServletRequest request,
			HttpServletResponse response) throws IOException, JSONException {
		
		/*
		 * get ip 
		 * if valid proceed
		 * 
		 * else 
		 * 
		 * bawal magload in
		 */
		
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
					JSONObject dataJSON = new JSONObject();

					String accessToken = UUIDGenerator.generateUniqueString();
					System.out.println(accessToken);
					userService.assignAccessToken(username, accessToken);

					dataJSON.put("userAccessToken", accessToken);
					dataJSON.put("isValid", "true");
					jsonResponse = JSONResponseCreator.createJSONResponse(
							"success", dataJSON, null);
				} else {
					JSONObject dataJSON = new JSONObject();
					dataJSON.put("isValid", "false");
					jsonResponse = JSONResponseCreator.createJSONResponse(
							"success", dataJSON, null);
				}
			} else {
				jsonResponse = JSONResponseCreator.createJSONResponse("fail",
						null, "Not an authorized client, access denied.");
			}

		} catch (JSONConverterException | JSONException
				| ClientAuthenticationServiceException | UserServiceException e) {
			jsonResponse = JSONResponseCreator.createJSONResponse("fail", null,
					"Process cannot be completed, an error has occured in the web server + "
							+ e.getMessage());
			e.printStackTrace();
		}

		writer.write(jsonResponse.toString());
	}

	@RequestMapping(value = "/user/register")
	public void register(HttpServletRequest request,
			HttpServletResponse response) throws JSONException, IOException {
		PrintWriter writer = response.getWriter();
		JSONObject jsonResponse = null;
		try {
			JSONObject json = GSONConverter.getJSONObjectFromReader(request
					.getReader());
			boolean isAuthorized = clientAuthenticationService
					.isFromAuthorizedClient(json);
			if (isAuthorized) {
				JSONObject data = JSONParser.getData(json);
				System.out.println(data);
				User user = GSONConverter.getGSONObjectGivenJsonObject(data,
						User.class);
				userService.addUser(user);

				JSONObject dataJSON = new JSONObject();
				String accessToken = UUIDGenerator.generateUniqueString();
				System.out.println(accessToken);
				userService.assignAccessToken(user.getUsername(), accessToken);

				dataJSON.put("userAccessToken", accessToken);
				jsonResponse = JSONResponseCreator.createJSONResponse(
						"success", dataJSON, null);
				System.out.println("finished well");
			} else {
				System.out.println("Unauthorized?");
				jsonResponse = JSONResponseCreator.createJSONResponse("fail",
						null, "Not an authorized client, access denied.");
			}

		} catch (JSONConverterException | JSONException
				| ClientAuthenticationServiceException | UserServiceException e) {
			e.printStackTrace();
			jsonResponse = JSONResponseCreator.createJSONResponse("fail", null,
					"Process cannot be completed, an error has occured in the web server + "
							+ e.getMessage());
			e.printStackTrace();
		} catch (UsernameAlreadyExistsException e) {
			JSONObject dataForResponse = new JSONObject();
			dataForResponse.put("usernameAlreadyExists", "true");
			jsonResponse = JSONResponseCreator.createJSONResponse("success",
					dataForResponse, "Duplicate Username Exception Occured!");
		}

		writer.write(jsonResponse.toString());
	}
}
