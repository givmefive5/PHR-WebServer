package com.example.tools;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONResponseCreator {

	public static String STATUS_SUCCESS = "success";
	public static String STATUS_ERROR = "error";

	public static JSONObject createJSONResponse(String status, Object data,
			String message) throws JSONException {
		JSONObject json = new JSONObject();
		json.append("status", status);
		json.append("data", GSONConverter.convertObjectToJSON(data));
		json.append("message", message);
		return json;
	}

	public static JSONObject createJSONResponse(String status,
			String jsonStringData, String message) throws JSONException {
		JSONObject json = new JSONObject();
		json.append("status", status);
		json.append("data", jsonStringData);
		json.append("message", message);
		return json;
	}

}
