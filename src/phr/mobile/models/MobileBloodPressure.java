package phr.mobile.models;

import java.sql.Date;

public class MobileBloodPressure extends MobileTrackerEntry {

	int systolic;
	int diastolic;
	
	public MobileBloodPressure(Integer entryID, Integer fbPostID,
			Date dateAdded, String status, String encodedImage, int systolic,
			int diastolic) {
		super(entryID, fbPostID, dateAdded, status, encodedImage);
		this.systolic = systolic;
		this.diastolic = diastolic;
	}

	public MobileBloodPressure(Integer fbPostID, Date dateAdded, String status,
			String encodedImage, int systolic, int diastolic) {
		super(fbPostID, dateAdded, status, encodedImage);
		this.systolic = systolic;
		this.diastolic = diastolic;
	}

	public MobileBloodPressure(Date dateAdded, String status,
			String encodedImage, int systolic, int diastolic) {
		super(dateAdded, status, encodedImage);
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
