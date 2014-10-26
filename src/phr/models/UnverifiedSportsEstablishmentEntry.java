package phr.models;

import java.sql.Timestamp;

public class UnverifiedSportsEstablishmentEntry extends TrackerEntry {
	
	String gymName;

	public UnverifiedSportsEstablishmentEntry(Integer entryID, User user,
			String facebookID, Timestamp timestamp, String status,
			PHRImage image, String gymName) {
		super(entryID, user, facebookID, timestamp, status, image);
		this.gymName = gymName;
	}

	public UnverifiedSportsEstablishmentEntry(User user, String facebookID,
			Timestamp timestamp, String status, PHRImage image, String gymName) {
		super(user, facebookID, timestamp, status, image);
		this.gymName = gymName;
	}

	public String getGymName() {
		return gymName;
	}

	public void setGymName(String gymName) {
		this.gymName = gymName;
	}
	
}
