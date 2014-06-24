package com.example.tools;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONMessageCreator {

	public static JSONObject createJSONError(String errorMessage)
			throws JSONException {
		JSONObject json = new JSONObject();
		json.append("error", errorMessage);
		return json;
	}

	public static JSONObject createJSONMessage(String message)
			throws JSONException {
		JSONObject json = new JSONObject();
		json.append("message", message);
		return json;
	}
}
