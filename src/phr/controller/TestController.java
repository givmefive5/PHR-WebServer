package phr.controller;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import phr.exceptions.SNSException;
import phr.service.BloodPressureService;
import phr.sns.datamining.service.FacebookFetcherService;
import phr.web.models.FBPost;

@Controller
public class TestController {

	@Autowired
	BloodPressureService bloodPressureService;

	@Autowired
	FacebookFetcherService facebookFetcherService;

	@RequestMapping(value = "/test")
	public void test(@RequestParam String userAccessToken) throws SNSException {
		Timestamp timestamp = new Timestamp(new Long("1411181266814"));
		System.out.println(timestamp);
		List<FBPost> posts = facebookFetcherService.getNewPostsAfterDate(
				timestamp, userAccessToken);

		for (FBPost p : posts) {
			System.out.println(p.getStatus() + " " + p.getPostType());
			if (p.getExtractedWords() != null)
				for (String s : p.getExtractedWords())
					System.out.println(s);
		}
	}
}
