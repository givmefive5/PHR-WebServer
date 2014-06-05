package com.example.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.dto.VerifyUserDTO;
import com.example.model.User;
import com.example.service.UserService;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
@Controller
public class UserController {
	
	@Autowired
	UserService userService;
	
	private JSONObject getJSONObject(BufferedReader reader) throws IOException, JSONException{
		StringBuilder sb = new StringBuilder();
		try {
		        String line;
		        while ((line = reader.readLine()) != null) {
		            sb.append(line).append('\n');
		        }
		    } finally {
		        reader.close();
		    }
		return new JSONObject(sb.toString());
	}
	
	private <T> T getGSONObject(BufferedReader reader, Class<T> cls){
		Gson gson = new Gson();

		return  gson.fromJson(reader, cls);
	}
	
	@RequestMapping(value = "/verifyUser", method = RequestMethod.POST)
	public void verifyUser(HttpServletRequest request, HttpServletResponse response){
		try {
			BufferedReader reader = request.getReader();
			VerifyUserDTO verifyUserDTO = (VerifyUserDTO) getGSONObject(reader, VerifyUserDTO.class);
			String username = verifyUserDTO.getUsername();
			String password = verifyUserDTO.getPassword();
			
			Boolean bool = userService.verifyUser(username, password);
			
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("isValid", bool.toString());
			
			Writer writer = response.getWriter();
			writer.write(jsonObj.toString());
		} catch (IOException | JSONException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="/getUserGivenUsername")
	public void getUserGivenUsername(HttpServletRequest request, HttpServletResponse response){
		try {
			JSONObject jsonObj = getJSONObject(request.getReader());
			String username = jsonObj.getString("username");
			
			User user = userService.getUserGivenUsername(username);
			if(user != null){
				Gson gson = new Gson();
				Writer writer = response.getWriter();
				writer.write(gson.toJson(user));
			}
		} catch (IOException | JSONException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="/userExists", method = RequestMethod.POST)
	public void userExists(HttpServletRequest request, HttpServletResponse response){
		try {
			BufferedReader reader = request.getReader();
			JSONObject jsonObj = getJSONObject(reader);
			String usernameToBeChecked = jsonObj.getString("usernameToBeChecked");
			
			Boolean bool = userService.userExists(usernameToBeChecked);
			
			jsonObj.put("userExists", bool.toString());
			
			Writer writer = response.getWriter();
			writer.write(jsonObj.toString());
		} catch (JSONException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
