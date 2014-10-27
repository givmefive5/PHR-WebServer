package phr.models;

import java.sql.Timestamp;
import java.util.List;

public class UnverifiedSportsEstablishmentEntry extends TrackerEntry {
	
	String extractedWord;
	SportEstablishment sportEstablishment;
	List<Activity> activities;

	public UnverifiedSportsEstablishmentEntry(Integer entryID, User user,
			String facebookID, Timestamp timestamp, String status,
			PHRImage image, String extractedWord, SportEstablishment sportEstablishment,  List<Activity> activities) {
		super(entryID, user, facebookID, timestamp, status, image);
		this.extractedWord = extractedWord;
		this.sportEstablishment = sportEstablishment;
		this.activities = activities;
	}

	public UnverifiedSportsEstablishmentEntry(User user, String facebookID,
			Timestamp timestamp, String status, PHRImage image, String extractedWord, SportEstablishment sportEstablishment, List<Activity> activities) {
		super(user, facebookID, timestamp, status, image);
		this.extractedWord = extractedWord;
		this.sportEstablishment = sportEstablishment;
		this.activities = activities;
	}

	
	public String getExtractedWord() {
		return extractedWord;
	}

	public void setExtractedWord(String extractedWord) {
		this.extractedWord = extractedWord;
	}

	public SportEstablishment getSportEstablishment() {
		return sportEstablishment;
	}

	public void setSportEstablishment(SportEstablishment sportEstablishment) {
		this.sportEstablishment = sportEstablishment;
	}

	public List<Activity> getActivities() {
		return activities;
	}

	public void setActivities(List<Activity> activities) {
		this.activities = activities;
	}
	
}
