package com.example.tools;

import java.io.BufferedReader;

import com.google.gson.Gson;

public final class GSONConverter {

	public <T> T getGSONObjectFromReader(BufferedReader reader, Class<T> classTypeToGenerate){
		Gson gson = new Gson();
		return  gson.fromJson(reader, classTypeToGenerate);
	}
	
	public <T> T getGSONObjectGivenJson(String jsonString, Class<T> classTypeToGenerate){
		Gson gson = new Gson();
		return gson.fromJson(jsonString, classTypeToGenerate);
	}
	
	public String convertObjectToJSON(Object objectToBeConverted){
		Gson gson = new Gson();
		return gson.toJson(objectToBeConverted);
	}
}
