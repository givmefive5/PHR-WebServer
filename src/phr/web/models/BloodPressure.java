package phr.web.models;

import java.sql.Timestamp;

public class BloodPressure extends TrackerEntry {

	int systolic;
	int diastolic;

	public BloodPressure(Integer entryID, User user, FBPost fbPost,
			Timestamp timestamp, String status, String imageFilePath, int systolic,
			int diastolic) {
		super(entryID, user, fbPost, timestamp, status, imageFilePath);
		this.systolic = systolic;
		this.diastolic = diastolic;
	}

	public BloodPressure(User user, FBPost fbPost, Timestamp timestamp,
			String status, String imageFilePath, int systolic, int diastolic) {
		super(user, fbPost, timestamp, status, imageFilePath);
		this.systolic = systolic;
		this.diastolic = diastolic;
	}

	public BloodPressure(Integer entryID, FBPost fbPost, Timestamp timestamp,
			String status, String imageFilePath, int systolic, int diastolic) {
		super(entryID, fbPost, timestamp, status, imageFilePath);
		this.systolic = systolic;
		this.diastolic = diastolic;
	}

	public BloodPressure(Integer entryID, User user, Timestamp timestamp,
			String status, String imageFilePath, int systolic, int diastolic) {
		super(entryID, user, timestamp, status, imageFilePath);
		this.systolic = systolic;
		this.diastolic = diastolic;
	}

	public BloodPressure(FBPost fbPost, Timestamp timestamp, String status,
			String imageFilePath, int systolic, int diastolic) {
		super(fbPost, timestamp, status, imageFilePath);
		this.systolic = systolic;
		this.diastolic = diastolic;
	}

	public BloodPressure(Timestamp timestamp, String status, String imageFilePath,
			int systolic, int diastolic) {
		super(timestamp, status, imageFilePath);
		this.systolic = systolic;
		this.diastolic = diastolic;
	}

	public int getSystolic() {
		return systolic;
	}

	public void setSystolic(int systolic) {
		this.systolic = systolic;
	}

	public int getDiastolic() {
		return diastolic;
	}

	public void setDiastolic(int diastolic) {
		this.diastolic = diastolic;
	}

}
