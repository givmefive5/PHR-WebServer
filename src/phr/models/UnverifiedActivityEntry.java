package phr.models;

import java.sql.Timestamp;

public class UnverifiedActivityEntry extends TrackerEntry {
	
	Activity activity;
	int durationInSeconds;
	Double calorieBurnedPerHour;
	
	public UnverifiedActivityEntry(Integer entryID, User user,
			String facebookID, Timestamp timestamp, String status,
			PHRImage image, Activity activity, int durationInSeconds,
			Double calorieBurnedPerHour) {
		super(entryID, user, facebookID, timestamp, status, image);
		this.activity = activity;
		this.durationInSeconds = durationInSeconds;
		this.calorieBurnedPerHour = calorieBurnedPerHour;
	}

	public UnverifiedActivityEntry(User user, String facebookID,
			Timestamp timestamp, String status, PHRImage image,
			Activity activity, int durationInSeconds,
			Double calorieBurnedPerHour) {
		super(user, facebookID, timestamp, status, image);
		this.activity = activity;
		this.durationInSeconds = durationInSeconds;
		this.calorieBurnedPerHour = calorieBurnedPerHour;
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
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
	
}
