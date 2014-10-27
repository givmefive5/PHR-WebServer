package phr.models;

import java.sql.Timestamp;
import java.util.List;

public class UnverifiedRestaurantEntry extends TrackerEntry {

	String extractedWord;
	Restaurant restaurant;
	List<Food> foods;

	public UnverifiedRestaurantEntry(Integer entryID, User user,
			String facebookID, Timestamp timestamp, String status,
			PHRImage image, String extractedWord, Restaurant restaurant, List<Food> foods) {
		super(entryID, user, facebookID, timestamp, status, image);
		this.extractedWord = extractedWord;
		this.restaurant = restaurant;
		this.foods = foods;
	}

	public UnverifiedRestaurantEntry(User user, String facebookID,
			Timestamp timestamp, String status, PHRImage image,
			String extractedWord, Restaurant restaurant,  List<Food> foods) {
		super(user, facebookID, timestamp, status, image);
		this.extractedWord = extractedWord;
		this.restaurant = restaurant;
		this.foods = foods;
	}

	public String getExtractedWord() {
		return extractedWord;
	}

	public void setExtractedWord(String extractedWord) {
		this.extractedWord = extractedWord;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	public List<Food> getFoods() {
		return foods;
	}

	public void setFoods(List<Food> foods) {
		this.foods = foods;
	}

}
