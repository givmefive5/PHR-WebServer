package phr.models;

import java.sql.Timestamp;

public class ActivityTrackerEntry extends TrackerEntry {

	Activity activity;
	double calorieBurnedPerHour;
	int durationInSeconds;
	
	public ActivityTrackerEntry(Integer entryID, User user, FBPost fbPost,
			Timestamp timestamp, String status, PHRImage image,
			Activity activity, double caloriesBurnedPerHour,
			int durationInSeconds) {
		super(entryID, user, fbPost, timestamp, status, image);
		this.activity = activity;
		this.calorieBurnedPerHour = caloriesBurnedPerHour;
		this.durationInSeconds = durationInSeconds;
	}

	public ActivityTrackerEntry(User user, FBPost fbPost, Timestamp timestamp,
			String status, PHRImage image, Activity activity,
			double caloriesBurnedPerHour, int durationInSeconds) {
		super(user, fbPost, timestamp, status, image);
		this.activity = activity;
		this.calorieBurnedPerHour = caloriesBurnedPerHour;
		this.durationInSeconds = durationInSeconds;
	}

	public ActivityTrackerEntry(Integer entryID, FBPost fbPost,
			Timestamp timestamp, String status, PHRImage image,
			Activity activity, double caloriesBurnedPerHour,
			int durationInSeconds) {
		super(entryID, fbPost, timestamp, status, image);
		this.activity = activity;
		this.calorieBurnedPerHour = caloriesBurnedPerHour;
		this.durationInSeconds = durationInSeconds;
	}

	public ActivityTrackerEntry(Integer entryID, User user,
			Timestamp timestamp, String status, PHRImage image,
			Activity activity, double caloriesBurnedPerHour,
			int durationInSeconds) {
		super(entryID, user, timestamp, status, image);
		this.activity = activity;
		this.calorieBurnedPerHour = caloriesBurnedPerHour;
		this.durationInSeconds = durationInSeconds;
	}

	public ActivityTrackerEntry(FBPost fbPost, Timestamp timestamp,
			String status, PHRImage image, Activity activity,
			double caloriesBurnedPerHour, int durationInSeconds) {
		super(fbPost, timestamp, status, image);
		this.activity = activity;
		this.calorieBurnedPerHour = caloriesBurnedPerHour;
		this.durationInSeconds = durationInSeconds;
	}

	public ActivityTrackerEntry(Timestamp timestamp, String status,
			PHRImage image, Activity activity, double caloriesBurnedPerHour,
			int durationInSeconds) {
		super(timestamp, status, image);
		this.activity = activity;
		this.calorieBurnedPerHour = caloriesBurnedPerHour;
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

	public void setCalorisBurnedPerHour(double caloriesBurnedPerHour) {
		this.calorieBurnedPerHour = caloriesBurnedPerHour;
	}

	public int getDurationInSeconds() {
		return durationInSeconds;
	}

	public void setDurationInSeconds(int durationInSeconds) {
		this.durationInSeconds = durationInSeconds;
	}
	
	
	
}
