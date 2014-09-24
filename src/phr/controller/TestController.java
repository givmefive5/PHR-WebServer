package phr.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import phr.exceptions.SNSException;
import phr.fatsecret.FatSecretFetcher;
import phr.fatsecret.FatSecretFood;

@Controller
public class TestController {

	FatSecretFetcher fatSecretFetcher = new FatSecretFetcher();

	@RequestMapping(value = "/test")
	public void test() throws SNSException, UnsupportedEncodingException,
			ClientProtocolException, GeneralSecurityException, IOException,
			JSONException {
		List<FatSecretFood> foodList = fatSecretFetcher.searchFood("bacon");
		for (FatSecretFood f : foodList) {
			System.out.println(f.getFood_name());
		}
	}
}
