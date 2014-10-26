package phr.models;

import java.sql.Timestamp;

public class UnverifiedFoodEntry extends TrackerEntry {
	
	Food food;
	double servingCount;
	
	public UnverifiedFoodEntry(Integer entryID, User user, String facebookID,
			Timestamp timestamp, String status, PHRImage image, Food food,
			double servingCount) {
		super(entryID, user, facebookID, timestamp, status, image);
		this.food = food;
		this.servingCount = servingCount;
	}

	public UnverifiedFoodEntry(User user, String facebookID,
			Timestamp timestamp, String status, PHRImage image, Food food,
			double servingCount) {
		super(user, facebookID, timestamp, status, image);
		this.food = food;
		this.servingCount = servingCount;
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
	
}
