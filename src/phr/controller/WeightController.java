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

import phr.exceptions.JSONConverterException;
import phr.exceptions.ServiceException;
import phr.exceptions.UserServiceException;
import phr.service.UserService;
import phr.service.WeightService;
import phr.tools.GSONConverter;
import phr.tools.JSONParser;
import phr.tools.JSONResponseCreator;
import phr.web.models.Weight;

@Controller
public class WeightController {

	@Autowired
	WeightService weightService;
	
	@Autowired
	UserService userService;

	@RequestMapping(value = "/tracker/addWeight", method = RequestMethod.POST)
	public void addWeight(HttpServletRequest request,
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
				
				Weight weight = GSONConverter
						.getGSONObjectGivenJsonObject(data.getJSONObject("weight"),
								Weight.class);
				weightService.addReturnEntryID(accessToken, weight);
				int entryID = weightService.getEntryId(weight);
				
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

		} catch (JSONException | ServiceException
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
