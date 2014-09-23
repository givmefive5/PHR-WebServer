package phr.web.models;

import java.sql.Timestamp;

public class FoodTrackerEntry extends TrackerEntry {
	
	Food food;
	double servingCount;
	
	public FoodTrackerEntry(Integer entryID, User user, FBPost fbPost,
			Timestamp timestamp, String status, PHRImage image, Food food,
			double servingCount) {
		super(entryID, user, fbPost, timestamp, status, image);
		this.food = food;
		this.servingCount = servingCount;
	}
	
	public FoodTrackerEntry(User user, FBPost fbPost, Timestamp timestamp,
			String status, PHRImage image, Food food, double servingCount) {
		super(user, fbPost, timestamp, status, image);
		this.food = food;
		this.servingCount = servingCount;
	}
	
	public FoodTrackerEntry(Integer entryID, FBPost fbPost,
			Timestamp timestamp, String status, PHRImage image, Food food,
			double servingCount) {
		super(entryID, fbPost, timestamp, status, image);
		this.food = food;
		this.servingCount = servingCount;
	}
	
	public FoodTrackerEntry(Integer entryID, User user, Timestamp timestamp,
			String status, PHRImage image, Food food, double servingCount) {
		super(entryID, user, timestamp, status, image);
		this.food = food;
		this.servingCount = servingCount;
	}
	
	public FoodTrackerEntry(FBPost fbPost, Timestamp timestamp, String status,
			PHRImage image, Food food, double servingCount) {
		super(fbPost, timestamp, status, image);
		this.food = food;
		this.servingCount = servingCount;
	}
	
	public FoodTrackerEntry(Timestamp timestamp, String status, PHRImage image,
			Food food, double servingCount) {
		super(timestamp, status, image);
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
