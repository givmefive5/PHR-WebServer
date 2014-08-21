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

import com.example.exceptions.ClientAuthenticationServiceException;
import com.example.exceptions.DataAccessException;
import com.example.exceptions.JSONConverterException;
import com.example.exceptions.LoggingException;
import com.example.exceptions.UserServiceException;
import com.example.exceptions.UsernameAlreadyExistsException;
import com.example.exceptions.ValidateIPServiceException;
import com.example.model.Log;
import com.example.model.User;
import com.example.service.ClientAuthenticationService;
import com.example.service.LogService;
import com.example.service.UserService;
import com.example.service.ValidateIPService;
import com.example.tools.GSONConverter;
import com.example.tools.IPRetriever;
import com.example.tools.JSONParser;
import com.example.tools.JSONResponseCreator;
import com.example.tools.TimestampHandler;
import com.example.tools.UUIDGenerator;

@Controller
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	ClientAuthenticationService clientAuthenticationService;

	@Autowired
	LogService logService;

	@Autowired
	ValidateIPService validateIPService;

	@RequestMapping(value = "/user/validateLogin")
	public void validateLogin(HttpServletRequest request,
			HttpServletResponse response) throws IOException, JSONException,
			LoggingException, DataAccessException, ValidateIPServiceException {
		String ip = IPRetriever.getIPAddressFromRequest(request);
		System.out.println(ip);

		Log log = null;
		PrintWriter writer = response.getWriter();
		JSONObject jsonResponse = null;

		if (validateIPService.isValidIP(ip)) {
			System.out.println("true");
			try {
				JSONObject json = GSONConverter.getJSONObjectFromReader(request
						.getReader());
				boolean isAuthorized = clientAuthenticationService
						.isFromAuthorizedClient(json);
				if (isAuthorized) {
					JSONObject data = JSONParser.getData(json);
					String username = data.getString("username");
					String hashedPassword = data.getString("hashedPassword");
					if (userService.isValidUser(new User(username,
							hashedPassword))) {
						validateIPService.clearAllIPRecords(ip);
						JSONObject dataJSON = new JSONObject();

						String accessToken = UUIDGenerator
								.generateUniqueString();
						System.out.println(accessToken);
						userService.assignAccessToken(username, accessToken);

						dataJSON.put("userAccessToken", accessToken);
						dataJSON.put("isValid", "true");
						jsonResponse = JSONResponseCreator.createJSONResponse(
								"success", dataJSON, null);
						log = new Log("User " + username + " has logged in.",
								ip, TimestampHandler.getCurrentTimestamp(),
								"UserController");
						validateIPService.clearAllIPRecords(ip);
					} else {
						JSONObject dataJSON = new JSONObject();
						dataJSON.put("isValid", "false");
						jsonResponse = JSONResponseCreator.createJSONResponse(
								"success", dataJSON, null);
						log = new Log(
								"INFO: A user tried to log in with incorrect information",
								ip, TimestampHandler.getCurrentTimestamp(),
								"UserController");
						validateIPService.addIPEntry(ip,
								TimestampHandler.getCurrentTimestamp());
					}
				} else {
					jsonResponse = JSONResponseCreator.createJSONResponse(
							"fail", null,
							"Not an authorized client, access denied.");
					log = new Log(
							"ALERT: Somebody tried to access the web server without the authorization IDs",
							ip, TimestampHandler.getCurrentTimestamp(),
							"UserController");
				}

			} catch (JSONException | ClientAuthenticationServiceException
					| UserServiceException e) {
				jsonResponse = JSONResponseCreator.createJSONResponse("fail",
						null,
						"Process cannot be completed, an error has occured in the web server + "
								+ e.getMessage());
				log = new Log(
						"ERROR: An error has occurred while processing a request.",
						ip, TimestampHandler.getCurrentTimestamp(),
						"UserController");
				e.printStackTrace();
			} catch (JSONConverterException e) {
				jsonResponse = JSONResponseCreator.createJSONResponse("fail",
						null,
						"Process cannot be completed, an error has occured in the web server + "
								+ e.getMessage());
				log = new Log(
						"ALERT: Somebody tried to access the web server without passing a JSONObject. Potential Attacker",
						ip, TimestampHandler.getCurrentTimestamp(),
						"UserController");
			}
		} else {
			System.out.println("false");
			JSONObject data = new JSONObject();
			data.put("isBlocked", "true");
			jsonResponse = JSONResponseCreator.createJSONResponse("fail", data,
					"Your IP is currently blocked, please try again later");
			log = new Log("ALERT: Somebody tried to login while he is blocked",
					ip, TimestampHandler.getCurrentTimestamp(),
					"UserController");
		}

		writer.write(jsonResponse.toString());
		logService.addLog(log);
	}

	@RequestMapping(value = "/user/register")
	public void register(HttpServletRequest request,
			HttpServletResponse response) throws JSONException, IOException,
			LoggingException {
		String ip = IPRetriever.getIPAddressFromRequest(request);
		System.out.println(ip);

		Log log = null;
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
				log = new Log("INFO: User " + user.getUsername()
						+ " has been successfully registered", ip,
						TimestampHandler.getCurrentTimestamp(),
						"UserController");
			} else {
				jsonResponse = JSONResponseCreator.createJSONResponse("fail",
						null, "Not an authorized client, access denied.");
				log = new Log(
						"ALERT: Somebody tried to access the web server without the authorization IDs",
						ip, TimestampHandler.getCurrentTimestamp(),
						"UserController");
			}

		} catch (JSONException | ClientAuthenticationServiceException
				| UserServiceException e) {
			e.printStackTrace();
			jsonResponse = JSONResponseCreator.createJSONResponse("fail", null,
					"Process cannot be completed, an error has occured in the web server + "
							+ e.getMessage());
			log = new Log(
					"ERROR: An error has occurred while processing a register request",
					ip, TimestampHandler.getCurrentTimestamp(),
					"UserController");
		} catch (UsernameAlreadyExistsException e) {
			JSONObject dataForResponse = new JSONObject();
			dataForResponse.put("usernameAlreadyExists", "true");
			jsonResponse = JSONResponseCreator.createJSONResponse("success",
					dataForResponse, "Duplicate Username Exception Occured!");
			log = new Log(
					"INFO: A user tried to register with a duplicate username",
					ip, TimestampHandler.getCurrentTimestamp(),
					"UserController");
		} catch (JSONConverterException e) {
			jsonResponse = JSONResponseCreator.createJSONResponse("fail", null,
					"ERROR: Process cannot be completed, an error has occured in the web server + "
							+ e.getMessage());
			log = new Log(
					"ALERT: Somebody tried to access the web server without passing a JSONObject. Potential Attacker",
					ip, TimestampHandler.getCurrentTimestamp(),
					"UserController");
		}

		writer.write(jsonResponse.toString());
		logService.addLog(log);
	}
}
