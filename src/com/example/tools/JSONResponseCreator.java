package com.example.tools;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONResponseCreator {

	public static String STATUS_SUCCESS = "success";
	public static String STATUS_ERROR = "error";

	public static JSONObject createJSONResponse(String status, Object data,
			String message) throws JSONException {
		JSONObject json = new JSONObject();
		json.put("status", status);
		json.put("data", GSONConverter.convertObjectToJSON(data));
		json.put("message", message);
		return json;
	}

	public static JSONObject createJSONResponse(String status,
			String jsonStringData, String message) throws JSONException {
		JSONObject json = new JSONObject();
		json.put("status", status);
		json.put("data", jsonStringData);
		json.put("message", message);
		return json;
	}

	public static JSONObject createJSONExceptionResponse(String message)
			throws JSONException {
		JSONObject json = new JSONObject();
		json.put("status", "fail");
		json.put("message", message);
		return json;
	}
}
