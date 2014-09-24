package phr.fatsecret;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.security.GeneralSecurityException;
import java.util.List;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import phr.tools.GSONConverter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

public class FatSecretFetcher {
	private final static String consumerKey = "953a55be24a745179b698c8e6acb7e09";
	private final static String sharedSecret = "3b9041ffab6d43ee8075777883c4f9ff";
	private final static String address = "http://platform.fatsecret.com/rest/server.api";

	private static Long getTimeStamp() {
		long timestamp = System.currentTimeMillis() / 1000;
		return timestamp;
	}

	private static String encode(String string)
			throws UnsupportedEncodingException {
		String encoded = URLEncoder.encode(string, "UTF-8");
		return encoded;
	}

	private static String computeSignature(String baseString, String keyString)
			throws GeneralSecurityException, UnsupportedEncodingException {

		SecretKey secretKey = null;

		byte[] keyBytes = keyString.getBytes();
		secretKey = new SecretKeySpec(keyBytes, "HmacSHA1");

		Mac mac = Mac.getInstance("HmacSHA1");

		mac.init(secretKey);

		byte[] text = baseString.getBytes();

		return new String(Base64.encodeBase64(mac.doFinal(text))).trim();
	}

	private static void getExercises() throws ClientProtocolException,
			IOException, GeneralSecurityException {
		Long timestamp = getTimeStamp();
		String params = "format=json&method=exercises.get&oauth_consumer_key="
				+ consumerKey + "&oauth_nonce=" + timestamp + "&"
				+ "oauth_signature_method=HMAC-SHA1&oauth_timestamp="
				+ timestamp + "&oauth_version=1.0";

		String signatureBaseString = "";
		signatureBaseString += "GET&" + encode(address) + "&" + encode(params);
		System.out.println("Signature Base String :");
		System.out.println(signatureBaseString + "\n");
		String signatureValue = computeSignature(signatureBaseString,
				sharedSecret + "&");
		System.out.println("Signature Value:");
		System.out.println(signatureValue + "\n");
		HttpClient client = new DefaultHttpClient();

		String newParams = "format=json&method=exercises.get&oauth_consumer_key="
				+ consumerKey
				+ "&oauth_nonce="
				+ timestamp
				+ "&"
				+ "oauth_signature="
				+ signatureValue
				+ "&"
				+ "oauth_signature_method=HMAC-SHA1&oauth_timestamp="
				+ timestamp + "&oauth_version=1.0";
		System.out.println("Http Request:");
		System.out.println(address + "?" + newParams + "\n");
		HttpGet get = new HttpGet(address + "?" + newParams);
		HttpResponse response = client.execute(get);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		out.close();
		response.getEntity().writeTo(out);
		JsonParser parser = new JsonParser();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		JsonElement el = parser.parse(out.toString());
		String output = gson.toJson(el);
		System.out.println(output);
	}

	public List<FatSecretFood> searchFood(String query)
			throws UnsupportedEncodingException, GeneralSecurityException,
			IOException, ClientProtocolException, JSONException {
		Long timestamp = getTimeStamp();
		String params = "format=json&method=foods.search&oauth_consumer_key="
				+ consumerKey + "&oauth_nonce=" + timestamp + "&"
				+ "oauth_signature_method=HMAC-SHA1&oauth_timestamp="
				+ timestamp + "&oauth_version=1.0&" + "search_expression="
				+ query;

		String signatureBaseString = "";
		signatureBaseString += "GET&" + encode(address) + "&" + encode(params);
		System.out.println("Signature Base String :");
		System.out.println(signatureBaseString + "\n");
		String signatureValue = computeSignature(signatureBaseString,
				sharedSecret + "&");
		System.out.println("Signature Value:");
		System.out.println(signatureValue + "\n");
		HttpClient client = new DefaultHttpClient();

		String newParams = "format=json&method=foods.search&oauth_consumer_key="
				+ consumerKey
				+ "&oauth_nonce="
				+ timestamp
				+ "&"
				+ "oauth_signature="
				+ signatureValue
				+ "&"
				+ "oauth_signature_method=HMAC-SHA1&oauth_timestamp="
				+ timestamp
				+ "&oauth_version=1.0&"
				+ "search_expression="
				+ query;
		System.out.println("Http Request:");
		System.out.println(address + "?" + newParams + "\n");
		HttpGet get = new HttpGet(address + "?" + newParams);
		HttpResponse response = client.execute(get);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		out.close();
		response.getEntity().writeTo(out);
		JsonParser parser = new JsonParser();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		JsonElement el = parser.parse(out.toString());
		String output = gson.toJson(el);
		System.out.println(output);
		JSONObject json = new JSONObject(output);
		String jsonArr = json.getJSONObject("foods").get("food").toString();
		Type type = new TypeToken<List<FatSecretFood>>() {
		}.getType();
		List<FatSecretFood> foodList = GSONConverter.convertJSONToObjectList(
				jsonArr, type);
		return foodList;
	}
}
