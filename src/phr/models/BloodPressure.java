package phr.models;

import java.sql.Timestamp;

public class BloodPressure extends TrackerEntry {

	int systolic;
	int diastolic;

	public BloodPressure(Integer entryID, User user, String facebookID,
			Timestamp timestamp, String status, PHRImage image, int systolic,
			int diastolic) {
		super(entryID, user, facebookID, timestamp, status, image);
		this.systolic = systolic;
		this.diastolic = diastolic;
	}

	public BloodPressure(User user, String facebookID, Timestamp timestamp,
			String status, PHRImage image, int systolic, int diastolic) {
		super(user, facebookID, timestamp, status, image);
		this.systolic = systolic;
		this.diastolic = diastolic;
	}

	public BloodPressure(Integer entryID, String facebookID, Timestamp timestamp,
			String status, PHRImage image, int systolic, int diastolic) {
		super(entryID, facebookID, timestamp, status, image);
		this.systolic = systolic;
		this.diastolic = diastolic;
	}

	public BloodPressure(Integer entryID, User user, Timestamp timestamp,
			String status, PHRImage image, int systolic, int diastolic) {
		super(entryID, user, timestamp, status, image);
		this.systolic = systolic;
		this.diastolic = diastolic;
	}

	public BloodPressure(String facebookID, Timestamp timestamp, String status,
			PHRImage image, int systolic, int diastolic) {
		super(facebookID, timestamp, status, image);
		this.systolic = systolic;
		this.diastolic = diastolic;
	}

	public BloodPressure(Timestamp timestamp, String status, PHRImage image,
			int systolic, int diastolic) {
		super(timestamp, status, image);
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
