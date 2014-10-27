package phr.models;

import java.sql.Timestamp;

public class UnverifiedFoodEntry extends TrackerEntry {
	
	Food food;
	double servingCount;
	String extractedWord;
	
	public UnverifiedFoodEntry(Integer entryID, User user, String facebookID,
			Timestamp timestamp, String status, PHRImage image, Food food,
			double servingCount, String extractedWord) {
		super(entryID, user, facebookID, timestamp, status, image);
		this.food = food;
		this.servingCount = servingCount;
		this.extractedWord = extractedWord;
	}

	public UnverifiedFoodEntry(User user, String facebookID,
			Timestamp timestamp, String status, PHRImage image, Food food,
			double servingCount, String extractedWord) {
		super(user, facebookID, timestamp, status, image);
		this.food = food;
		this.servingCount = servingCount;
		this.extractedWord = extractedWord;
	}

	public Food getFood() {
		return food;
	}

	public void setFood(Food food) {
		this.food = food;
	}

	public double getServingCount() {
		return servingCount;
	}

	public void setServingCount(double servingCount) {
		this.servingCount = servingCount;
	}

	public String getExtractedWord() {
		return extractedWord;
	}

	public void setExtractedWord(String extractedWord) {
		this.extractedWord = extractedWord;
	}

}
