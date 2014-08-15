package com.example.tools;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONParser {

	public static String getClientID(JSONObject json) throws JSONException {
		JSONObject authObject = new JSONObject(json.get("auth").toString());

		return authObject.getString("clientID");
	}

	public static String getClientPassword(JSONObject json)
			throws JSONException {
		JSONObject authObject = new JSONObject(json.get("auth").toString());
		;

		return authObject.getString("clientPassword");
	}

	public static JSONObject getData(JSONObject json) throws JSONException {
		JSONObject dataObject = new JSONObject(json.get("data").toString());
		System.out.println(dataObject);
		return dataObject;
	}

}
