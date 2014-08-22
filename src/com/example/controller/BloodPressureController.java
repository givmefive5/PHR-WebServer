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

import com.example.exceptions.BloodPressureServiceException;
import com.example.exceptions.ClientAuthenticationServiceException;
import com.example.exceptions.JSONConverterException;
import com.example.exceptions.LoggingException;
import com.example.exceptions.UserServiceException;
import com.example.model.BloodPressure;
import com.example.model.Log;
import com.example.service.BloodPressureService;
import com.example.service.ClientAuthenticationService;
import com.example.service.LogService;
import com.example.service.UserService;
import com.example.tools.GSONConverter;
import com.example.tools.IPRetriever;
import com.example.tools.JSONParser;
import com.example.tools.JSONResponseCreator;
import com.example.tools.TimestampHandler;

@Controller
public class BloodPressureController {

	@Autowired
	BloodPressureService bloodPressureService;
	@Autowired
	ClientAuthenticationService clientAuthenticationService;
	@Autowired
	UserService userService;
	@Autowired
	LogService logService;

	@RequestMapping(value = "/tracker/addBloodPressure", method = RequestMethod.POST)
	public void addBloodPressure(HttpServletRequest request,
			HttpServletResponse response) throws IOException, JSONException,
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
				String accessToken = data.getString("accessToken");
				String username = data.getString("username");
				if (userService.isValidAccessToken(accessToken, username)) {
					BloodPressure bloodPressure = GSONConverter
							.getGSONObjectGivenJsonObject(
									data.getJSONObject("bloodPressure"),
									BloodPressure.class);
					bloodPressureService.addBloodPressure(username,
							bloodPressure);
					jsonResponse = JSONResponseCreator.createJSONResponse(
							"success", null, "Process has been completed");
					log = new Log("INFO: Blood Pressure entry of user: "
							+ username + " has been successfully added.", ip,
							TimestampHandler.getCurrentTimestamp(),
							"BloodPressureController");
				} else {
					JSONObject dataForResponse = new JSONObject();
					dataForResponse.put("isValidAccessToken", "false");
					jsonResponse = JSONResponseCreator
							.createJSONResponse("success", dataForResponse,
									"Access token is invalid, please ask user to log in again.");
					log = new Log(
							"INFO: Somebody tried to perform a request with an expired access token",
							ip, TimestampHandler.getCurrentTimestamp(),
							"BloodPressureController");
				}
			} else {
				System.out.println("Unauthorized Client");
				jsonResponse = JSONResponseCreator.createJSONResponse("fail",
						null, "Not an authorized client, access denied.");
				log = new Log(
						"ALERT: Somebody tried to access the web server without the authorization IDs",
						ip, TimestampHandler.getCurrentTimestamp(),
						"BloodPressureController");
			}

		} catch (JSONConverterException | ClientAuthenticationServiceException
				| JSONException | UserServiceException
				| BloodPressureServiceException e) {
			jsonResponse = JSONResponseCreator.createJSONResponse("fail", null,
					"ERROR: Process cannot be completed, an error has occured in the web server + "
							+ e.getMessage());
			log = new Log(
					"ALERT: Somebody tried to access the web server without passing a JSONObject. Potential Attacker",
					ip, TimestampHandler.getCurrentTimestamp(),
					"BloodPressureController");
		}
		System.out.println(jsonResponse);
		writer.write(jsonResponse.toString());
		logService.addLog(log);
	}
}
