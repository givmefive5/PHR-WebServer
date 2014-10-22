package phr.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.sql.Timestamp;
import java.util.ArrayList;
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
import phr.fatsecret.FatSecretFood;
import phr.service.VerificationService;
import phr.service.impl.VerificationServiceImpl;
import phr.sns.datamining.filter.KeywordsExtractor;
import phr.sns.datamining.service.FacebookFetcherService;
import phr.sns.datamining.serviceimpl.FacebookFetcherServiceImpl;
import phr.tools.ImageHandler;

@Controller
public class TestController {
	FacebookFetcherService fetcher = new FacebookFetcherServiceImpl();

	@RequestMapping(value = "/test")
	public void test(@RequestParam String userFBAccessToken)
			throws SNSException, UnsupportedEncodingException,
			ClientProtocolException, GeneralSecurityException, IOException,
			JSONException {

		VerificationService verification = new VerificationServiceImpl();
		try {
			Timestamp startDate = new Timestamp(0);
			System.out.println("Hey hey");
			verification.updateListOfUnverifiedPosts(
					"4e443873-82b1-428a-b8e6-3cf4c3e1378e", userFBAccessToken,
					startDate);
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
		List<FatSecretFood> foods = FatSecretFetcher.searchFood(searchQuery);
		for (FatSecretFood food : foods) {
			System.out.println(food.getFood_name());
		}
	}

}
