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

import com.example.exceptions.JSONConverterException;
import com.example.exceptions.TrackerServiceException;
import com.example.exceptions.UserServiceException;
import com.example.model.BloodPressure;
import com.example.service.BloodPressureService;
import com.example.service.UserService;
import com.example.tools.GSONConverter;
import com.example.tools.JSONParser;
import com.example.tools.JSONResponseCreator;

@Controller
public class BloodPressureController {

	@Autowired
	BloodPressureService bloodPressureService;
	@Autowired
	UserService userService;

	@RequestMapping(value = "/tracker/addBloodPressure", method = RequestMethod.POST)
	public void addBloodPressure(HttpServletRequest request,
			HttpServletResponse response) throws IOException, JSONException {

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
				BloodPressure bloodPressure = GSONConverter
						.getGSONObjectGivenJsonObject(
								data.getJSONObject("bloodPressure"),
								BloodPressure.class);
				bloodPressureService.addBloodPressure(username, bloodPressure);
				int entryID = bloodPressureService.getEntryID(bloodPressure);

				JSONObject dataForResponse = new JSONObject();
				dataForResponse.put("entryID", entryID);
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

		} catch (JSONException | TrackerServiceException
				| JSONConverterException | UserServiceException e) {
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
