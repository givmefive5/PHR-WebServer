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
	
	private VerifyUserDTO getGSONObject(BufferedReader reader){
		Gson gson = new Gson();

		return  gson.fromJson(reader, VerifyUserDTO.class);
	}
	
	@RequestMapping(value = "/verifyUser", method = RequestMethod.POST)
	public void verifyUser(HttpServletRequest request, Model model, HttpServletResponse response) throws JSONException, IOException{
		BufferedReader reader = request.getReader();
		
		/*VerifyUserDTO verifyUserDTO = getGSONObject(reader);
		String username = verifyUserDTO.getUsername();
		String password = verifyUserDTO.getPassword();*/
		
		JSONObject jsonObj = getJSONObject(reader);
		String username = jsonObj.getString("username");
		String password = jsonObj.getString("password");
		
		System.out.println("username " + username + " password " + password);
		Boolean bool = userService.verifyUser(username, password);
		System.out.println(bool);
		
		jsonObj = new JSONObject();
		jsonObj.put("isValid", bool.toString());
		Writer writer = response.getWriter();
		writer.write(jsonObj.toString());
	}
	
	@RequestMapping("/Login")
	public String login(){
		return "login";
	}
	
	@RequestMapping("/verifyUserWeb")
	public String verifyUserWeb(@RequestParam("username") String username, @RequestParam("password") String password, Model model) throws JSONException{
		Boolean bool = userService.verifyUser(username, password);
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("isValid", bool.toString());
		model.addAttribute("result", jsonObj);
		return "home";
	}
}
