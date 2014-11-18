package phr.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import phr.exceptions.DataAccessException;
import phr.exceptions.FatSecretFetcherException;
import phr.exceptions.ImageHandlerException;
import phr.exceptions.SNSException;
import phr.exceptions.ServiceException;
import phr.fatsecret.FatSecretFetcher;
import phr.models.Food;
import phr.service.FacebookPostService;
import phr.service.VerificationService;
import phr.service.impl.FacebookPostServiceImpl;
import phr.service.impl.VerificationServiceImpl;
import phr.sns.datamining.filter.KeywordsExtractor;
import phr.sns.datamining.service.FacebookFetcherService;
import phr.sns.datamining.serviceimpl.FacebookFetcherServiceImpl;
import phr.tools.ImageHandler;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.Post;
import facebook4j.auth.AccessToken;

@Controller
public class TestController {
	FacebookFetcherService fetcher = new FacebookFetcherServiceImpl();
	FacebookPostService fbPostService = new FacebookPostServiceImpl();

	@RequestMapping(value = "/test")
	public void test(@RequestParam String userFBAccessToken)
			throws SNSException, UnsupportedEncodingException,
			ClientProtocolException, GeneralSecurityException, IOException,
			JSONException, ParseException {

		VerificationService verification = new VerificationServiceImpl();
		try {
			String accessToken = "2f839900-1703-49b0-9a5d-52b40a5bde90";
			Timestamp startDate = fbPostService
					.getLatestPostTimestamp(accessToken);
			String date_s = " 2014-11-17 00:00:00.0";
			SimpleDateFormat dt = new SimpleDateFormat("yyyyy-mm-dd hh:mm:ss");
			Date date = dt.parse(date_s);
			Timestamp timestamp = new Timestamp(date.getTime());
			verification.updateListOfUnverifiedPosts(accessToken,
					userFBAccessToken, timestamp);
		} catch (ServiceException e) {
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/test2")
	public void test2() throws SNSException, UnsupportedEncodingException,
			ClientProtocolException, GeneralSecurityException, IOException,
			JSONException, DataAccessException {
		KeywordsExtractor keywordsExtractor = new KeywordsExtractor();
		List<String> samplePosts = new ArrayList<>();
		samplePosts
				.add("Halo-Halo #filipinofood #filipino #dessert #ube #gulaman");
		samplePosts
				.add("Inihaw na Pusit and Lechon Kawali. Stomach is happy #filipinofood #yum #blessed #mmm #ooogurl #sofine");
		samplePosts.add("Ramen for four ");
		samplePosts
				.add("All I think about is food... #food #gyoza #frieddumplings #mysunshine #onacloudyday");
		samplePosts.add("First stop! #approved #bellevue #lunchboxlab");

		for (String s : samplePosts) {
			String[] wordsFound = keywordsExtractor.extractFoodNames(s);
			System.out.println("Actual post : " + s);
			System.out.println("Foods");
			for (int i = 0; i < wordsFound.length; i++) {
				System.out.println(wordsFound[i]);
			}
			System.out.println();
			System.out.println("Restaurants");
			wordsFound = keywordsExtractor.extractRestaurantNames(s);
			for (int i = 0; i < wordsFound.length; i++) {
				System.out.println(wordsFound[i]);
			}
			System.out.println();
		}
	}

	@RequestMapping(value = "/test4")
	public void test4() throws ImageHandlerException {
		BufferedImage image = new BufferedImage(10, 10, 10);
		String encodedImage = ImageHandler.encodeBufferedImage(image);
		System.out.println(encodedImage);
		String filePath = ImageHandler.saveImage_ReturnFilePath(encodedImage);
		System.out.println(filePath);
	}

	@RequestMapping(value = "/test5")
	public void test5(@RequestParam String searchQuery)
			throws FatSecretFetcherException {
		List<Food> foods = FatSecretFetcher.searchFood(searchQuery);
		for (Food food : foods) {
			System.out.println(food.getName());
			System.out.println(food.getCalorie() + "kcal " + food.getProtein());
			System.out.println(food.getServing() + " " + food.getFat() + "g "
					+ food.getCarbohydrate() + "g");

		}
	}

	@RequestMapping(value = "/test6")
	public void test6(@RequestParam String userFBAccessToken)
			throws SNSException, UnsupportedEncodingException,
			ClientProtocolException, GeneralSecurityException, IOException,
			JSONException, ParseException, FacebookException {
		final String appID = "458502710946706";
		final String appSecret = "c41ccfbd5ff58c87342f4df5911d2d88";
		Facebook facebook;
		facebook = new FacebookFactory().getInstance();
		facebook.setOAuthAppId(appID, appSecret);
		String permissions = "email,user_groups,user_status,read_stream, user_photos, user_actions:instapp";
		facebook.setOAuthPermissions(permissions);
		facebook.setOAuthAccessToken(new AccessToken(userFBAccessToken, null));
		List<Post> posts = facebook.getTagged();
		for (Post p : posts) {
			if (p.getMessage() != null)
				System.out.println(p.getMessage());

		}
	}
}
