package com.example.mobile.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.exceptions.JSONConverterException;
import com.example.exceptions.SNSException;
import com.example.snsapi.FacebookAPI;
import com.example.snsapi.FacebookAPIImpl;
import com.example.tools.GSONConverter;

@Controller
public class FacebookController {

	FacebookAPI fbAPI = new FacebookAPIImpl();

	@RequestMapping(value = "/facebook/getFBPosts")
	public void getFacebookPostsAndAnalyzeFromAPI(HttpServletRequest request,
			HttpServletResponse response) throws IOException, JSONException {
		try {
			JSONObject jsonObj = GSONConverter.getJSONObjectFromReader(request
					.getReader());
			String username = jsonObj.getString("username");
			String accessToken = jsonObj.getString("accessToken");
			List<Object> posts = fbAPI.getPosts(accessToken);
			response.getWriter().write("success");
		} catch (JSONConverterException | IOException | JSONException
				| SNSException e) {
			/*
			 * JSONObject errorJson = JSONResponseCreator
			 * .createJSONError(e.getMessage());
			 * response.getWriter().write(errorJson.toString());
			 */
		}
	}
}
