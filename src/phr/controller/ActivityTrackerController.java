package phr.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import phr.exceptions.EntryNotFoundException;
import phr.exceptions.JSONConverterException;
import phr.exceptions.ServiceException;
import phr.exceptions.UserServiceException;
import phr.models.ActivityTrackerEntry;
import phr.service.ActivityTrackerService;
import phr.service.UserTrackerService;
import phr.tools.GSONConverter;
import phr.tools.JSONParser;
import phr.tools.JSONResponseCreator;

public class ActivityTrackerController {
	
	@Autowired
	ActivityTrackerService activityTrackerService;
	
	@Autowired
	UserTrackerService userService;

	@RequestMapping(value = "/tracker/addActivityTrackerEntry", method = RequestMethod.POST)
	public void addActivity(HttpServletRequest request,
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
				ActivityTrackerEntry activity = GSONConverter
						.getGSONObjectGivenJsonObject(
								data.getJSONObject("objectToAdd"),
								ActivityTrackerEntry.class);
				int entryID = activityTrackerService.addReturnEntryID(accessToken,
						activity);

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

		} catch (JSONException | ServiceException | JSONConverterException
				| UserServiceException e) {
			jsonResponse = JSONResponseCreator.createJSONResponse("fail", null,
					"Process cannot be completed, an error has occured in the web server + "
							+ e.getMessage());
			e.printStackTrace();
		}
		System.out.println("Response JSON To Be Sent Back To App: "
				+ jsonResponse);
		writer.write(jsonResponse.toString());
	}

	@RequestMapping(value = "/tracker/editActivityTrackerEntry", method = RequestMethod.POST)
	public void editActivity(HttpServletRequest request,
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
				ActivityTrackerEntry activity = GSONConverter
						.getGSONObjectGivenJsonObject(
								data.getJSONObject("objectToEdit"),
								ActivityTrackerEntry.class);
				activityTrackerService.edit(accessToken, activity);

				jsonResponse = JSONResponseCreator.createJSONResponse(
						"success", null, "Process has been completed");
			} else {
				JSONObject dataForResponse = new JSONObject();
				dataForResponse.put("isValidAccessToken", "false");
				jsonResponse = JSONResponseCreator
						.createJSONResponse("success", dataForResponse,
								"Access token is invalid, please ask user to log in again.");
			}

		} catch (JSONException | ServiceException | JSONConverterException
				| UserServiceException e) {
			jsonResponse = JSONResponseCreator.createJSONResponse("fail", null,
					"Process cannot be completed, an error has occured in the web server + "
							+ e.getMessage());
			e.printStackTrace();
		} catch (EntryNotFoundException e) {
			jsonResponse = JSONResponseCreator.createJSONResponse("fail", null,
					"The entry to be edited was not found in the database + "
							+ e.getMessage());
			e.printStackTrace();
		}
		System.out.println("Response JSON To Be Sent Back To App: "
				+ jsonResponse);
		writer.write(jsonResponse.toString());
	}

	@RequestMapping(value = "/tracker/deleteActivityTrackerEntry", method = RequestMethod.POST)
	public void deleteActivity(HttpServletRequest request,
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
				ActivityTrackerEntry activity = GSONConverter
						.getGSONObjectGivenJsonObject(
								data.getJSONObject("objectToDelete"),
								ActivityTrackerEntry.class);
				activityTrackerService.delete(accessToken, activity);

				jsonResponse = JSONResponseCreator.createJSONResponse(
						"success", null, "Process has been completed");
			} else {
				JSONObject dataForResponse = new JSONObject();
				dataForResponse.put("isValidAccessToken", "false");
				jsonResponse = JSONResponseCreator
						.createJSONResponse("success", dataForResponse,
								"Access token is invalid, please ask user to log in again.");
			}

		} catch (JSONException | ServiceException | JSONConverterException
				| UserServiceException e) {
			jsonResponse = JSONResponseCreator.createJSONResponse("fail", null,
					"Process cannot be completed, an error has occured in the web server + "
							+ e.getMessage());
			e.printStackTrace();
		} catch (EntryNotFoundException e) {
			jsonResponse = JSONResponseCreator.createJSONResponse("fail", null,
					"The entry to be deleted was not found in the database + "
							+ e.getMessage());
			e.printStackTrace();
		}
		System.out.println("Response JSON To Be Sent Back To App: "
				+ jsonResponse);
		writer.write(jsonResponse.toString());
	}

	@RequestMapping(value = "/tracker/getAllActivityTrackerEntry")
	public void getAll(HttpServletRequest request, HttpServletResponse response)
			throws IOException, JSONException {
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
				List<ActivityTrackerEntry> activityList = activityTrackerService
						.getAll(accessToken);
				JSONArray jsonArray = GSONConverter
						.convertListToJSONArray(activityList);

				JSONObject dataForResponse = new JSONObject();
				dataForResponse.put("array", jsonArray);
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

		} catch (JSONException | ServiceException | JSONConverterException
				| UserServiceException e) {
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
