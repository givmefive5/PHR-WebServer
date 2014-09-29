package phr.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import phr.exceptions.JSONConverterException;

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

	public static JSONObject convertObjectToJSON(Object objectToBeConverted)
			throws JSONException {
		if (objectToBeConverted.getClass().equals(JSONObject.class))
			return (JSONObject) objectToBeConverted;
		Gson gson = new Gson();
		return new JSONObject(gson.toJson(objectToBeConverted));
	}

	public static <T> List<T> convertJSONToObjectList(String jsonString,
			Type type) {
		Gson gson = new Gson();
		List<T> list = gson.fromJson(jsonString, type);
		return list;
	}

	public static <T> JSONArray convertListToJSONArray(List<T> list) {
		JSONArray array = new JSONArray(list);
		return array;
	}
}
