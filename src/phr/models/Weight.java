package phr.models;

import java.sql.Timestamp;

public class Weight extends TrackerEntry {

	private double weightInPounds;

	public Weight(Integer entryID, User user, String facebookID,
			Timestamp timestamp, String status, PHRImage image,
			double weightInPounds) {
		super(entryID, user, facebookID, timestamp, status, image);
		this.weightInPounds = weightInPounds;
	}

	public Weight(User user, String facebookID, Timestamp timestamp, String status,
			PHRImage image, double weightInPounds) {
		super(user, facebookID, timestamp, status, image);
		this.weightInPounds = weightInPounds;
	}

	public Weight(Integer entryID, String facebookID, Timestamp timestamp,
			String status, PHRImage image, double weightInPounds) {
		super(entryID, facebookID, timestamp, status, image);
		this.weightInPounds = weightInPounds;
	}

	public Weight(Integer entryID, User user, Timestamp timestamp,
			String status, PHRImage image, double weightInPounds) {
		super(entryID, user, timestamp, status, image);
		this.weightInPounds = weightInPounds;
	}

	public Weight(String facebookID, Timestamp timestamp, String status,
			PHRImage image, double weightInPounds) {
		super(facebookID, timestamp, status, image);
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
