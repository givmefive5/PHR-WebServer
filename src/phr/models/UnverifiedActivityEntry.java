package phr.models;

import java.sql.Timestamp;

public class UnverifiedActivityEntry {
	
	Integer entryID;
	Timestamp timestamp;
	String activityName;
	int durationInSeconds;
	Double calorieBurnedPerHour;
	String status;
	PHRImage image;
	User user;
	Integer fbPostID;
	
	public UnverifiedActivityEntry(Integer entryID, Timestamp timestamp,
			String activityName, int durationInSeconds,
			Double calorieBurnedPerHour, String status, PHRImage image,
			User user, Integer fbPostID) {
		super();
		this.entryID = entryID;
		this.timestamp = timestamp;
		this.activityName = activityName;
		this.durationInSeconds = durationInSeconds;
		this.calorieBurnedPerHour = calorieBurnedPerHour;
		this.status = status;
		this.image = image;
		this.user = user;
		this.fbPostID = fbPostID;
	}

	public UnverifiedActivityEntry(Timestamp timestamp, String activityName,
			int durationInSeconds, Double calorieBurnedPerHour, String status,
			PHRImage image, User user, Integer fbPostID) {
		super();
		this.timestamp = timestamp;
		this.activityName = activityName;
		this.durationInSeconds = durationInSeconds;
		this.calorieBurnedPerHour = calorieBurnedPerHour;
		this.status = status;
		this.image = image;
		this.user = user;
		this.fbPostID = fbPostID;
	}

	public Integer getEntryID() {
		return entryID;
	}

	public void setEntryID(Integer entryID) {
		this.entryID = entryID;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public int getDurationInSeconds() {
		return durationInSeconds;
	}

	public void setDurationInSeconds(int durationInSeconds) {
		this.durationInSeconds = durationInSeconds;
	}

	public Double getCalorieBurnedPerHour() {
		return calorieBurnedPerHour;
	}

	public void setCalorieBurnedPerHour(Double calorieBurnedPerHour) {
		this.calorieBurnedPerHour = calorieBurnedPerHour;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public PHRImage getImage() {
		return image;
	}

	public void setImage(PHRImage image) {
		this.image = image;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getFbPostID() {
		return fbPostID;
	}

	public void setFbPostID(Integer fbPostID) {
		this.fbPostID = fbPostID;
	}
	
}
