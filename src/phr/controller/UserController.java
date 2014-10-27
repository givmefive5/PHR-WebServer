package phr.controller;

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

import phr.exceptions.DataAccessException;
import phr.exceptions.JSONConverterException;
import phr.exceptions.UserServiceException;
import phr.exceptions.UsernameAlreadyExistsException;
import phr.models.User;
import phr.service.UserService;
import phr.tools.GSONConverter;
import phr.tools.ImageHandler;
import phr.tools.JSONParser;
import phr.tools.JSONResponseCreator;
import phr.tools.UUIDGenerator;

@Controller
public class UserController {

	@Autowired
	UserService userService;

	ImageHandler imageHandler = new ImageHandler();

	@RequestMapping(value = "/user/validateLogin", method = RequestMethod.POST)
	public void validateLogin(HttpServletRequest request,
			HttpServletResponse response) throws IOException, JSONException,
			DataAccessException {

		PrintWriter writer = response.getWriter();
		JSONObject jsonResponse = null;

		try {
			JSONObject json = GSONConverter.getJSONObjectFromReader(request
					.getReader());
			System.out.println(json);
			JSONObject data = JSONParser.getData(json);
			String username = data.getString("username");
			String hashedPassword = data.getString("hashedPassword");
			if (userService.isValidUser(new User(username, hashedPassword))) {
				JSONObject dataJSON = new JSONObject();

				String accessToken = UUIDGenerator.generateUniqueString();
				System.out.println("Assigned Access Token to Username: "
						+ username + " with Access Token: " + accessToken);
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

		} catch (JSONException | JSONConverterException | UserServiceException e) {
			jsonResponse = JSONResponseCreator.createJSONResponse("fail", null,
					"Process cannot be completed, an error has occured in the web server + "
							+ e.getMessage());
			e.printStackTrace();
		}
		System.out.println("Response JSON To Be Sent Back To App: "
				+ jsonResponse);
		writer.write(jsonResponse.toString());
	}

	@RequestMapping(value = "/user/register", method = RequestMethod.POST)
	public void register(HttpServletRequest request,
			HttpServletResponse response) throws JSONException, IOException {
		PrintWriter writer = response.getWriter();
		JSONObject jsonResponse = null;
		try {
			JSONObject json = GSONConverter.getJSONObjectFromReader(request
					.getReader());
			JSONObject data = JSONParser.getData(json);
			User user = GSONConverter.getGSONObjectGivenJsonObject(data,
					User.class);
			userService.addUser(user);

			JSONObject dataJSON = new JSONObject();
			String accessToken = UUIDGenerator.generateUniqueString();
			userService.assignAccessToken(user.getUsername(), accessToken);
			System.out.println("Assigned Access Token to Username: "
					+ user.getUsername() + ". Access Token = " + accessToken);
			dataJSON.put("userAccessToken", accessToken);
			jsonResponse = JSONResponseCreator.createJSONResponse("success",
					dataJSON, null);

		} catch (JSONException | JSONConverterException | UserServiceException e) {
			e.printStackTrace();
			jsonResponse = JSONResponseCreator.createJSONResponse("fail", null,
					"Process cannot be completed, an error has occured in the web server + "
							+ e.getMessage());
		} catch (UsernameAlreadyExistsException e) {
			JSONObject dataForResponse = new JSONObject();
			dataForResponse.put("usernameAlreadyExists", "true");
			jsonResponse = JSONResponseCreator.createJSONResponse("success",
					dataForResponse, "Duplicate Username Exception Occured!");
		}
		System.out.println("Response JSON To Be Sent Back To App: "
				+ jsonResponse);
		writer.write(jsonResponse.toString());
	}

	@RequestMapping(value = "/user/checkIfUsernameExists")
	public void checkIfUsernameExists(HttpServletRequest request,
			HttpServletResponse response) throws JSONException, IOException {
		PrintWriter writer = response.getWriter();
		JSONObject jsonResponse = null;
		try {
			JSONObject json = GSONConverter.getJSONObjectFromReader(request
					.getReader());
			JSONObject data = JSONParser.getData(json);
			String username = data.getString("username");
			boolean exists = userService.usernameAlreadyExists(username);

			JSONObject dataJSON = new JSONObject();
			dataJSON.put("exists", exists);
			jsonResponse = JSONResponseCreator.createJSONResponse("success",
					dataJSON, null);

		} catch (JSONException | JSONConverterException | UserServiceException e) {
			e.printStackTrace();
			jsonResponse = JSONResponseCreator.createJSONResponse("fail", null,
					"Process cannot be completed, an error has occured in the web server + "
							+ e.getMessage());
		}
		System.out.println("Response JSON To Be Sent Back To App: "
				+ jsonResponse);
		writer.write(jsonResponse.toString());
	}

	@RequestMapping(value = "user/get")
	public void get(HttpServletRequest request, HttpServletResponse response)
			throws JSONException, IOException {
		PrintWriter writer = response.getWriter();
		JSONObject jsonResponse = null;
		try {
			JSONObject json = GSONConverter.getJSONObjectFromReader(request
					.getReader());
			System.out.println("JSON From Request: " + json);
			JSONObject data = JSONParser.getData(json);
			String accessToken = data.getString("accessToken");
			String username = data.getString("username");
			if (userService.isValidAccessToken(accessToken, username)) {
				User user = userService.getUserGivenAccessToken(accessToken);

				JSONObject dataForResponse = new JSONObject();
				dataForResponse.put("user",
						GSONConverter.convertObjectToJSON(user));
				jsonResponse = JSONResponseCreator.createJSONResponse(
						"success", dataForResponse,
						"Process has been completed");
			} else {
				JSONObject dataForResponse = new JSONObject();
				dataForResponse.put("isValidAccessToken", "false");
				jsonResponse = JSONResponseCreator
						.createJSONResponse("success", dataForResponse,
								"Access token is invalid, please ask user to log in again.");
			}

		} catch (JSONException | JSONConverterException | UserServiceException e) {
			jsonResponse = JSONResponseCreator.createJSONResponse("fail", null,
					"Process cannot be completed, an error has occured in the web server + "
							+ e.getMessage());
			e.printStackTrace();
		}
		System.out.println("Response JSON To Be Sent Back To App: "
				+ jsonResponse);
		writer.write(jsonResponse.toString());
	}

	@RequestMapping(value = "/user/edit", method = RequestMethod.POST)
	public void editBloodPressure(HttpServletRequest request,
			HttpServletResponse response) throws JSONException, IOException {
		PrintWriter writer = response.getWriter();
		JSONObject jsonResponse = null;
		try {
			JSONObject json = GSONConverter.getJSONObjectFromReader(request
					.getReader());
			System.out.println("JSON From Request: " + json);
			JSONObject data = JSONParser.getData(json);
			String accessToken = data.getString("accessToken");
			String username = data.getString("username");
			if (userService.isValidAccessToken(accessToken, username)) {
				User user = GSONConverter.getGSONObjectGivenJsonObject(
						data.getJSONObject("user"), User.class);
				userService.edit(user);

				jsonResponse = JSONResponseCreator.createJSONResponse(
						"success", null, "Process has been completed");
			} else {
				JSONObject dataForResponse = new JSONObject();
				dataForResponse.put("isValidAccessToken", "false");
				jsonResponse = JSONResponseCreator
						.createJSONResponse("success", dataForResponse,
								"Access token is invalid, please ask user to log in again.");
			}

		} catch (JSONException | JSONConverterException | UserServiceException e) {
			jsonResponse = JSONResponseCreator.createJSONResponse("fail", null,
					"Process cannot be completed, an error has occured in the web server + "
							+ e.getMessage());
			e.printStackTrace();
		}
		System.out.println("Response JSON To Be Sent Back To App: "
				+ jsonResponse);
		writer.write(jsonResponse.toString());
	}
}
