package com.example.tools;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONParser {

	public static String getClientID(JSONObject json) throws JSONException {
		JSONObject authObject = json.getJSONObject("auth");

		return authObject.getString("clientID");
	}

	public static String getClientPassword(JSONObject json)
			throws JSONException {
		JSONObject authObject = json.getJSONObject("auth");

		return authObject.getString("clientPassword");
	}

	public static JSONObject getData(JSONObject json) throws JSONException {
		JSONObject data = json.getJSONObject("data");
		return data;
	}

}
