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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import phr.exceptions.JSONConverterException;
import phr.exceptions.ServiceException;
import phr.exceptions.UserServiceException;
import phr.service.FoodService;
import phr.service.UserTrackerService;
import phr.tools.GSONConverter;
import phr.tools.JSONParser;
import phr.tools.JSONResponseCreator;
import phr.web.models.BloodPressure;
import phr.web.models.Food;
import phr.web.models.FoodTrackerEntry;

@Controller
public class FoodController {
	
	@Autowired
	FoodService foodService;
	
	@Autowired
	UserTrackerService userService;

	@RequestMapping(value = "/foodlist/add")
	public void add(HttpServletRequest request, HttpServletResponse response) throws JSONException, IOException{
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
				Food food = GSONConverter
						.getGSONObjectGivenJsonObject(
								data.getJSONObject("objectToAdd"),
								Food.class);
				int entryID = foodService.addReturnEntryID(food);

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
	
	@RequestMapping(value = "/foodlist/getAll")
	public void getAll(HttpServletRequest request, HttpServletResponse response) throws JSONException, IOException{
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
				List<Food> foodList = foodService.getAll();
				JSONArray jsonArray = GSONConverter
						.convertListToJSONArray(foodList);

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
	
	@RequestMapping(value = "/foodlist/search")
	public void search(HttpServletRequest request, HttpServletResponse response) throws JSONException, IOException{
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
				String searchQuery = data.getString("searchQuery");
				List<Food> foodList = foodService
						.search(searchQuery);
				JSONArray jsonArray = GSONConverter
						.convertListToJSONArray(foodList);

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
