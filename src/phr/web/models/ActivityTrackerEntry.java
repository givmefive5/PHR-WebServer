package phr.web.models;

import java.sql.Timestamp;

public class ActivityTrackerEntry extends TrackerEntry {

	Activity activity;
	double calorieBurnedPerHour;
	int durationInSeconds;
	
	public ActivityTrackerEntry(Integer entryID, User user, FBPost fbPost,
			Timestamp timestamp, String status, PHRImage image,
			Activity activity, double calorisBurnedPerHour,
			int durationInSeconds) {
		super(entryID, user, fbPost, timestamp, status, image);
		this.activity = activity;
		this.calorieBurnedPerHour = calorisBurnedPerHour;
		this.durationInSeconds = durationInSeconds;
	}

	public ActivityTrackerEntry(User user, FBPost fbPost, Timestamp timestamp,
			String status, PHRImage image, Activity activity,
			double calorisBurnedPerHour, int durationInSeconds) {
		super(user, fbPost, timestamp, status, image);
		this.activity = activity;
		this.calorieBurnedPerHour = calorisBurnedPerHour;
		this.durationInSeconds = durationInSeconds;
	}

	public ActivityTrackerEntry(Integer entryID, FBPost fbPost,
			Timestamp timestamp, String status, PHRImage image,
			Activity activity, double calorisBurnedPerHour,
			int durationInSeconds) {
		super(entryID, fbPost, timestamp, status, image);
		this.activity = activity;
		this.calorieBurnedPerHour = calorisBurnedPerHour;
		this.durationInSeconds = durationInSeconds;
	}

	public ActivityTrackerEntry(Integer entryID, User user,
			Timestamp timestamp, String status, PHRImage image,
			Activity activity, double calorisBurnedPerHour,
			int durationInSeconds) {
		super(entryID, user, timestamp, status, image);
		this.activity = activity;
		this.calorieBurnedPerHour = calorisBurnedPerHour;
		this.durationInSeconds = durationInSeconds;
	}

	public ActivityTrackerEntry(FBPost fbPost, Timestamp timestamp,
			String status, PHRImage image, Activity activity,
			double calorisBurnedPerHour, int durationInSeconds) {
		super(fbPost, timestamp, status, image);
		this.activity = activity;
		this.calorieBurnedPerHour = calorisBurnedPerHour;
		this.durationInSeconds = durationInSeconds;
	}

	public ActivityTrackerEntry(Timestamp timestamp, String status,
			PHRImage image, Activity activity, double calorisBurnedPerHour,
			int durationInSeconds) {
		super(timestamp, status, image);
		this.activity = activity;
		this.calorieBurnedPerHour = calorisBurnedPerHour;
		this.durationInSeconds = durationInSeconds;
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public double getCalorisBurnedPerHour() {
		return calorieBurnedPerHour;
	}

	public void setCalorisBurnedPerHour(double calorisBurnedPerHour) {
		this.calorieBurnedPerHour = calorisBurnedPerHour;
	}

	public int getDurationInSeconds() {
		return durationInSeconds;
	}

	public void setDurationInSeconds(int durationInSeconds) {
		this.durationInSeconds = durationInSeconds;
	}
	
	
	
}
