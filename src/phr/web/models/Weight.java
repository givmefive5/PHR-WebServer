package phr.web.models;

import java.sql.Date;
import java.sql.Timestamp;

public class Weight extends TrackerEntry {

	private double weightInPounds;

	public Weight(Integer entryID, User user, FBPost fbPost, Timestamp timestamp,
			String status, String imageFilePath, double weightInPounds) {
		super(entryID, user, fbPost, timestamp, status, imageFilePath);
		this.weightInPounds = weightInPounds;
	}

	public Weight(User user, FBPost fbPost, Timestamp timestamp, String status,
			String imageFilePath, double weightInPounds) {
		super(user, fbPost, timestamp, status, imageFilePath);
		this.weightInPounds = weightInPounds;
	}

	public Weight(Integer entryID, FBPost fbPost, Timestamp timestamp,
			String status, String imageFilePath, double weightInPounds) {
		super(entryID, fbPost, timestamp, status, imageFilePath);
		this.weightInPounds = weightInPounds;
	}

	public Weight(Integer entryID, User user, Timestamp timestamp, String status,
			String imageFilePath, double weightInPounds) {
		super(entryID, user, timestamp, status, imageFilePath);
		this.weightInPounds = weightInPounds;
	}

	public Weight(FBPost fbPost, Timestamp timestamp, String status,
			String imageFilePath, double weightInPounds) {
		super(fbPost, timestamp, status, imageFilePath);
		this.weightInPounds = weightInPounds;
	}

	public Weight(Timestamp timestamp, String status, String imageFilePath,
			double weightInPounds) {
		super(timestamp, status, imageFilePath);
		this.weightInPounds = weightInPounds;
	}

}
