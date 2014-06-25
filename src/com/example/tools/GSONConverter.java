package com.example.tools;

import java.io.BufferedReader;
import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.exceptions.JSONConverterException;
import com.google.gson.Gson;

public final class GSONConverter {

	public static <T> T getGSONObjectFromReader(BufferedReader reader,
			Class<T> classTypeToGenerate) {
		Gson gson = new Gson();
		return gson.fromJson(reader, classTypeToGenerate);
	}

	public static <T> T getGSONObjectGivenJsonString(String jsonString,
			Class<T> classTypeToGenerate) {
		Gson gson = new Gson();
		return gson.fromJson(jsonString, classTypeToGenerate);
	}

	public static <T> T getGSONObjectGivenJsonObject(JSONObject json,
			Class<T> classTypeToGenerate) {
		Gson gson = new Gson();
		String jsonString = json.toString();
		return gson.fromJson(jsonString, classTypeToGenerate);
	}

	public static JSONObject getJSONObjectFromReader(BufferedReader reader)
			throws JSONConverterException {
		StringBuilder sb = new StringBuilder();
		try {
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line).append('\n');
			}
			reader.close();
			return new JSONObject(sb.toString());
		} catch (IOException | JSONException e) {
			throw new JSONConverterException("Cannot convert string to json.");
		}

	}

	public static String convertObjectToJSON(Object objectToBeConverted) {
		if (objectToBeConverted.getClass().equals(JSONObject.class))
			return objectToBeConverted.toString();
		Gson gson = new Gson();
		return gson.toJson(objectToBeConverted);
	}
}
