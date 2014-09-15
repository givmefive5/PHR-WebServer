package phr.web.models;

import java.sql.Date;

public class Weight extends TrackerEntry {

	private double weightInPounds;

	public Weight(Integer entryID, User user, FBPost fbPost, Date dateAdded,
			String status, String imageFilePath, double weightInPounds) {
		super(entryID, user, fbPost, dateAdded, status, imageFilePath);
		this.weightInPounds = weightInPounds;
	}

	public Weight(User user, FBPost fbPost, Date dateAdded, String status,
			String imageFilePath, double weightInPounds) {
		super(user, fbPost, dateAdded, status, imageFilePath);
		this.weightInPounds = weightInPounds;
	}

	public Weight(Integer entryID, FBPost fbPost, Date dateAdded,
			String status, String imageFilePath, double weightInPounds) {
		super(entryID, fbPost, dateAdded, status, imageFilePath);
		this.weightInPounds = weightInPounds;
	}

	public Weight(Integer entryID, User user, Date dateAdded, String status,
			String imageFilePath, double weightInPounds) {
		super(entryID, user, dateAdded, status, imageFilePath);
		this.weightInPounds = weightInPounds;
	}

	public Weight(FBPost fbPost, Date dateAdded, String status,
			String imageFilePath, double weightInPounds) {
		super(fbPost, dateAdded, status, imageFilePath);
		this.weightInPounds = weightInPounds;
	}

	public Weight(Date dateAdded, String status, String imageFilePath,
			double weightInPounds) {
		super(dateAdded, status, imageFilePath);
		this.weightInPounds = weightInPounds;
	}

}
