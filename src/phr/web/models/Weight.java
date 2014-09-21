package phr.web.models;

import java.sql.Timestamp;

public class Weight extends TrackerEntry {

	private double weightInPounds;

	public Weight(Integer entryID, User user, FBPost fbPost,
			Timestamp timestamp, String status, PHRImage image,
			double weightInPounds) {
		super(entryID, user, fbPost, timestamp, status, image);
		this.weightInPounds = weightInPounds;
	}

	public Weight(User user, FBPost fbPost, Timestamp timestamp, String status,
			PHRImage image, double weightInPounds) {
		super(user, fbPost, timestamp, status, image);
		this.weightInPounds = weightInPounds;
	}

	public Weight(Integer entryID, FBPost fbPost, Timestamp timestamp,
			String status, PHRImage image, double weightInPounds) {
		super(entryID, fbPost, timestamp, status, image);
		this.weightInPounds = weightInPounds;
	}

	public Weight(Integer entryID, User user, Timestamp timestamp,
			String status, PHRImage image, double weightInPounds) {
		super(entryID, user, timestamp, status, image);
		this.weightInPounds = weightInPounds;
	}

	public Weight(FBPost fbPost, Timestamp timestamp, String status,
			PHRImage image, double weightInPounds) {
		super(fbPost, timestamp, status, image);
		this.weightInPounds = weightInPounds;
	}

	public Weight(Timestamp timestamp, String status, PHRImage image,
			double weightInPounds) {
		super(timestamp, status, image);
		this.weightInPounds = weightInPounds;
	}

	public double getWeightInPounds() {
		return weightInPounds;
	}

	public void setWeightInPounds(double weightInPounds) {
		this.weightInPounds = weightInPounds;
	}

}
