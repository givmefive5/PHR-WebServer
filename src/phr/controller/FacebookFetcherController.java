package phr.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FacebookFetcherController {

	@RequestMapping(value = "/fb/getAllPosts")
	public void getAllPostOfAUser(HttpServletRequest request,
			HttpServletResponse response) {

	}

	@RequestMapping(value = "/fb/getAllPostsAfterDate")
	public void getAllPostsOfAUserAfterADate(HttpServletRequest request,
			HttpServletResponse response) {

	}
}
